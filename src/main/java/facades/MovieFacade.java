package facades;

import dtos.MovieDTO;
import edu.emory.mathcs.backport.java.util.Arrays;
import entities.Movie;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
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

    public void populate(){
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        em.persist(new Movie(1977, "Star Wars - A new hope", Arrays.asList(new String[]{"Mark Hamill", "Harrison Ford", "Carrie Fisher"})));
        em.persist(new Movie(1980, "Star Wars - Empire strikes back", Arrays.asList(new String[]{"Mark Hamill", "Harrison Ford", "Carrie Fisher", "Billy Dee Williams"})));
        em.persist(new Movie(1983, "Star Wars - Return of the jedi", Arrays.asList(new String[]{"Mark Hamill", "Harrison Ford", "Carrie Fisher", "Anthony Daniels"})));
        em.getTransaction().commit();
        em.close();
    }
}
