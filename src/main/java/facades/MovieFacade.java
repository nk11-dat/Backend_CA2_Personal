package facades;

import entities.Actor;
import entities.Movie;
import dtos.MovieDTO;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MovieFacade
{
    private static MovieFacade instance;
    private static EntityManagerFactory emf;

    //Private Constructor to ensure Singleton
    private MovieFacade() {}



    public static MovieFacade getInstance(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new MovieFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public List<MovieDTO> getAll(){
        EntityManager em = getEntityManager();
        try{
            TypedQuery<Movie> query = em.createQuery("select m from Movie m", Movie.class);
            List<Movie> movies = query.getResultList();
            return MovieDTO.getDTOs(movies);
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

    //public PersonDTO createPerson(PersonDTO personDTO)
    //    {
    //        Cityinfo ci = findCityInfo(new Cityinfo(personDTO.getAddress().getIdCITY().getCity(), personDTO.getAddress().getIdCITY().getZipcode()));
    //        Address address = createAdress(new Address(personDTO.getAddress().getStreet(), personDTO.getAddress().getAditionalInfo(), ci));
    //        Person person = new Person(address, personDTO.getFirstName(), personDTO.getLastName(), personDTO.getAge(), personDTO.getGender(), personDTO.getEmail());
    //
    //        EntityManager em = getEntityManager();
    //        try {
    //            em.getTransaction().begin();
    //            em.merge(person);
    //            em.flush(); //Behandel JPA som et offenligt toilet
    //            em.getTransaction().commit();
    //        } finally {
    //            em.close();
    //        }
    //        return new PersonDTO(person);
    //    }
    public void createMovie(MovieDTO movieDTO){
        EntityManager em = getEntityManager();
        List<Actor> actors = new ArrayList<>();

        for (MovieDTO.ActorInnerDTO actor : movieDTO.getActors()) {
            actors.add(findOrCreateActor(actor.getActorName()));
        }
        //TODO: Check om filmen findes i forvejen!
        Movie result;
        try {
            TypedQuery<Movie> query = em.createQuery("select m from Movie m where m.title = :title and m.year = :year and m.actors = :actors", Movie.class);
            query.setParameter("title", movieDTO.getTitle());
            query.setParameter("year", movieDTO.getYear());
            query.setParameter("actors", actors); //TODO: findActorsByMovie???
            result = query.getSingleResult();
//            return result;
        } catch (NoResultException e) {
            System.out.println("Weee shit don't work!...");
        }

        em.getTransaction().begin();
//        em.persist(new Movie(1977, "Star Wars - A new hope", Arrays.asList(new Actor[]{new Actor("Mark Hamill"), "Harrison Ford", "Carrie Fisher"})));
        em.persist(new Movie(movieDTO.getTitle(), movieDTO.getYear(), actors));
        em.getTransaction().commit();
        em.close();
    }

    private Actor findOrCreateActor(String actorName) {
        EntityManager em = getEntityManager();
        Actor result;
        try {
            TypedQuery<Actor> query = em.createQuery("select a from Actor a where a.actorName = :name", Actor.class);
            query.setParameter("name", actorName);
            result = query.getSingleResult();
            return result;

        } catch (NoResultException e) {
            Actor actor = new Actor(actorName);
            em.getTransaction().begin();
            em.persist(actor);
            em.flush(); //Behandel JPA som et offenligt toilet
            em.getTransaction().commit();
            System.out.println("Stop dig selv... det virker... gider ikke mer'");
            return actor;
        } finally {
            em.close();
        }
    }

    public void populate(){
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
//        em.persist(new Movie(1977, "Star Wars - A new hope", Arrays.asList(new String[]{"Mark Hamill", "Harrison Ford", "Carrie Fisher"})));
//        em.persist(new Movie(1980, "Star Wars - Empire strikes back", Arrays.asList(new String[]{"Mark Hamill", "Harrison Ford", "Carrie Fisher", "Billy Dee Williams"})));
//        em.persist(new Movie(1983, "Star Wars - Return of the jedi", Arrays.asList(new String[]{"Mark Hamill", "Harrison Ford", "Carrie Fisher", "Anthony Daniels"})));
        em.getTransaction().commit();
        em.close();
    }
}
