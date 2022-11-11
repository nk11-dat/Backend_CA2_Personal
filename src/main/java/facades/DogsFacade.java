package facades;

import dtos.CatDTO;
import dtos.DogDTO;
import entities.Cat;
import entities.Dog;
import utils.CallableHttpUtils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
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

//    public MovieDTO createMovie(MovieDTO movieDTO){
//        EntityManager em = getEntityManager();
//        List<Actors> actors = new ArrayList<>();
//
//        for (MovieDTO.ActorInnerDTO actor : movieDTO.getActors()) {
//            actors.add(findOrCreateActor(actor.getActorName()));
//        }
//        //TODO: Check om filmen findes i forvejen!
//        Movie result;
//        try {
//            TypedQuery<Movie> query = em.createQuery("select m from Movie m where m.Title = :title and m.Year = :year and m.actors = :actors", Movie.class);
//            query.setParameter("title", movieDTO.getTitle());
//            query.setParameter("year", movieDTO.getYear());
//            query.setParameter("actors", actors); //TODO: findActorsByMovie???
//            result = query.getSingleResult();
//            return new MovieDTO(result);
//        } catch (NoResultException e) {
//            em.getTransaction().begin();
////        em.persist(new Movie(1977, "Star Wars - A new hope", Arrays.asList(new Actors[]{new Actors("Mark Hamill"), "Harrison Ford", "Carrie Fisher"})));
//            Movie movie = new Movie(movieDTO.getTitle(), movieDTO.getYear(), actors);
//            em.persist(movie);
//            em.getTransaction().commit();
////            System.out.println("Weee shit don't work!...");
//            return new MovieDTO(movie);
//        } finally {
//            em.close();
//        }
//    }

//    private Actors findOrCreateActor(String actorNames) {
//        EntityManager em = getEntityManager();
//        Actors result;
//        try {
//            TypedQuery<Actors> query = em.createQuery("select a from Actors a where a.actors = :names", Actors.class);
//            query.setParameter("names", actorNames);
//            result = query.getSingleResult();
//            return result;
//
//        } catch (NoResultException e) {
//            Actors actors = new Actors(actorNames);
//            em.getTransaction().begin();
//            em.persist(actors);
//            em.flush(); //Behandel JPA som et offenligt toilet
//            em.getTransaction().commit();
//            System.out.println("Stop dig selv... det virker... gider ikke mer'");
//            return actors;
//        } finally {
//            em.close();
//        }
//    }

    public void populate(){
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
//        em.persist(new Movie("Star Wars - A new hope", 1977, "Mark Hamill, Harrison Ford, Carrie Fisher, Billy Dee Williams"));
//        em.persist(new Movie(1980, "Star Wars - Empire strikes back", Arrays.asList(new String[]{"Mark Hamill", "Harrison Ford", "Carrie Fisher", "Billy Dee Williams"})));
//        em.persist(new Movie(1983, "Star Wars - Return of the jedi", Arrays.asList(new String[]{"Mark Hamill", "Harrison Ford", "Carrie Fisher", "Anthony Daniels"})));
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
