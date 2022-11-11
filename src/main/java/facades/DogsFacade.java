package facades;

import dtos.DogDTO;
import entities.Dog;
import entities.Weight;
import utils.CallableHttpUtils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class DogsFacade
{
    private static DogsFacade instance;
    private static EntityManagerFactory emf;

    //Private Constructor to ensure Singleton
    private DogsFacade() {}



    public static DogsFacade getInstance(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new DogsFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public List<DogDTO> getAll(){
        EntityManager em = getEntityManager();
        try{
            TypedQuery<Dog> query = em.createQuery("select d from Dog d", Dog.class);
            List<Dog> dogs = query.getResultList();
            return DogDTO.getDTOs(dogs);
        }
        finally {
            em.close();
        }
    }

    public long countAll(){
        EntityManager em = getEntityManager();
        try{
            long count;
            TypedQuery<Long> query = em.createQuery("select count(m) from Movie m", Long.class);
            count = query.getSingleResult();
            return count;
        }
        finally {
            em.close();
        }
    }

    public DogDTO createDog(DogDTO dogDTO){
        EntityManager em = getEntityManager();

        //TODO: Check om filmen findes i forvejen!
        Dog result;
        try {
//            TypedQuery<Dog> query = em.createQuery("select d from Dog d where d.id = :id", Dog.class);
//            query.setParameter("id", dogDTO.getId());
////            query.setParameter("year", dogDTO.getYear());
////            query.setParameter("actors", actors); //TODO: findActorsByMovie???
//            result = query.getSingleResult();
            result = em.find(Dog.class, dogDTO.getId());
            if (result == null)
                throw new NoResultException("Couldn't find a dog in the DB with the id=" + dogDTO.getId());
            return new DogDTO(result);
        } catch (NoResultException e) {
            em.getTransaction().begin();
            //Integer id, String name, String lifeSpan, Weight weight) {
            Weight weight = new Weight(dogDTO.getWeight().getImperial(), dogDTO.getWeight().getMetric());
            Dog dog = new Dog(dogDTO.getId(), dogDTO.getName(), dogDTO.getLife_span(), weight);
            em.persist(dog);
            em.persist(dog.getWeight());
            em.getTransaction().commit();
//            System.out.println("Weee shit don't work!...");
            return new DogDTO(dog);
        } finally {
            em.close();
        }
    }

    public void populate(Dog doggy){
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        em.persist(doggy);
        em.persist(doggy.getWeight());
        //TODO: hvad med vægten...?
        em.getTransaction().commit();
        em.close();
    }

    public List<String> parallelRun(List<String> urls) throws ExecutionException, InterruptedException
    {
        ExecutorService es = Executors.newCachedThreadPool();
        List<Future<String>> futures = new ArrayList<>();
        List<String> results = new ArrayList<>();

        for (String url : urls) {
            Future<String> temp = es.submit(new CallableHttpUtils(url));
            futures.add(temp);
        }
        for (Future<String> f : futures) {
            String temp = f.get();
            results.add(temp);
        }
        return results;
    }
}
