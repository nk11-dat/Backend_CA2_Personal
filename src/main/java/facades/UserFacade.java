package facades;

import dtos.DogDTO;
import entities.Dog;
import entities.User;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.WebApplicationException;

import security.errorhandling.AuthenticationException;

/**
 * @author lam@cphbusiness.dk
 */
public class UserFacade {

    private static EntityManagerFactory emf;
    private static UserFacade instance;

    private UserFacade() {
    }

    /**
     *
     * @param _emf
     * @return the instance of this facade.
     */
    public static UserFacade getUserFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new UserFacade();
        }
        return instance;
    }

    public User getVeryfiedUser(String username, String password) throws AuthenticationException {
        EntityManager em = emf.createEntityManager();
        User user;
        try {
            user = em.find(User.class, username);
            if (user == null || !user.verifyPassword(password)) {
                throw new AuthenticationException("Invalid user name or password");
            }
        } finally {
            em.close();
        }
        return user;
    }

    public User fiedUser(String username) {
        EntityManager em = emf.createEntityManager();
        User user;
        try {
            user = em.find(User.class, username);
            if (user == null) {
                throw new NullPointerException("No user found with the username: " + username);
            }
        } finally {
            em.close();
        }
        return user;
    }

    //    public Person addHobby(Integer personId, Integer hobbyId)
//    {
//        EntityManager em = getEntityManager();
//        try {
//            em.getTransaction().begin();
//            Person person = em.find(Person.class, personId);
//            if (person == null)
//                throw new WebApplicationException("Person doesn't exist with id= " + personId);
//            Hobby hobby = em.find(Hobby.class, hobbyId);
//            if (hobby == null)
//                throw new WebApplicationException("Hobby doesn't exist with id= this is hardcoded....!¿?" + personId);
//            // new Hobby(hobbyDTO.getName(),hobbyDTO.getWikiLink(), hobbyDTO.getCategory(), hobbyDTO.getType());
//            person.addHobby(hobby);
//            em.getTransaction().commit();
//            return person;
//        } finally {
//            em.close();
//        }
//    }

    public User addDogToUser(String userName, Integer dogId)
    {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            User user = em.find(User.class, userName);
            if (user == null)
                throw new WebApplicationException("User doesn't exist with username= " + userName);
            Dog dog = em.find(Dog.class, dogId);
            if (dog == null)
                throw new WebApplicationException("Dog doesn't exist with id= " + dogId);
            // new Hobby(hobbyDTO.getName(),hobbyDTO.getWikiLink(), hobbyDTO.getCategory(), hobbyDTO.getType());
            user.addDog(dog);
            em.getTransaction().commit();
            return user;
        } finally {
            em.close();
        }
    }

    public DogDTO removeDogFromUser(String userName, int dogId)
    {
        EntityManager em = emf.createEntityManager();
        try {
            User user = em.find(User.class, userName);
            if (user == null)
                throw new WebApplicationException("User doesn't exist with username= " + userName);
            Dog dog = em.find(Dog.class, dogId);
            if (dog == null)
                throw new WebApplicationException("Dog doesn't exist with id= " + dogId);
            em.getTransaction().begin();
            user.removeDog(dog);
            em.getTransaction().commit();
            return new DogDTO(dog);
        } finally {
            em.close();
        }
    }

}
