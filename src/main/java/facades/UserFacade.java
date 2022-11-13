package facades;

import dtos.DogDTO;
import entities.Dog;
import entities.Role;
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

    public User createUser(String username, String password, String role) throws AuthenticationException {
        EntityManager em = emf.createEntityManager();
        User user = new User(username, password);

        em.getTransaction().begin();
        Role userRole = new Role(role);
        user.addRole(userRole);
//        em.persist(userRole);
        em.persist(user);
        em.getTransaction().commit();
        System.out.println("PW: " + user.getUserPass());
        System.out.println("Testing user with OK password: " + user.verifyPassword(password));
        System.out.println("Testing user with wrong password: " + user.verifyPassword(password+"1"));
        System.out.println("Created TEST Users");
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
    public User createUser(User user) throws AuthenticationException {
        EntityManager em = emf.createEntityManager();

//        em.getTransaction().begin();
//        Role userRole = new Role(role);
//        user.addRole(userRole);
//        em.persist(userRole);
//        em.persist(user);
//        em.getTransaction().commit();
//        System.out.println("PW: " + user.getUserPass());
//        System.out.println("Testing user with OK password: " + user.verifyPassword(password));
//        System.out.println("Testing user with wrong password: " + user.verifyPassword(password+"1"));
//        System.out.println("Created TEST Users");
//        try {
//            user = em.find(User.class, username);
//            if (user == null || !user.verifyPassword(password)) {
//                throw new AuthenticationException("Invalid user name or password");
//            }
//        } finally {
//            em.close();
//        }
        return user;
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
