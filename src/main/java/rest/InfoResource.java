package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.DogDTO;
import entities.User;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

import facades.UserFacade;
import security.errorhandling.AuthenticationException;
import utils.EMF_Creator;

/**
 * @author lam@cphbusiness.dk
 */
@Path("info")
public class InfoResource
{
    
    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    @Context
    private UriInfo context;
    private static final UserFacade userFacade = UserFacade.getUserFacade(EMF);
    @Context
    SecurityContext securityContext;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getInfoForAll() {
        return "{\"msg\":\"Hello anonymous\"}";
    }

    //Just to verify if the database is setup
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("all")
    public String allUsers() {

        EntityManager em = EMF.createEntityManager();
        try {
            TypedQuery<User> query = em.createQuery ("select u from User u",entities.User.class);
            List<User> users = query.getResultList();
//            return "[" + users.size() + "]";
            String json = GSON.toJson(users.get(0));
            return json;
        } finally {
            em.close();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("user")
    @RolesAllowed({"user", "admin"}) //add all userroles here
    public String getFromUser() {
        String thisuserName = securityContext.getUserPrincipal().getName();
        return "{\"msg\": \"Hello: " + thisuserName + "\"}";
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("admin")
    @RolesAllowed("admin")
    public String getFromAdmin() {
        String thisuser = securityContext.getUserPrincipal().getName();
        return "{\"msg\": \"Hello to (admin) User: " + thisuser + "\"}";
    }

    @POST
    @Path("create/user")
    @Produces(MediaType.APPLICATION_JSON)
//    public String addDog(@PathParam("userName")String userName, String input) {
    public String createUser()
    {

        try {
            userFacade.createUser("dogOwner420", "dogs", "user");
        } catch (AuthenticationException e) {
            throw new RuntimeException(e);
        }
        //Get Username... få det fra PathParam?

//        //create dog and weight
//        String json = input;
//        System.out.println(json);
//        json = json.replace("[", "");
//        json = json.replace("]", "");
//        User createdUser = userFacade.createUser(userName); //TODO: Hvordan kan dette gøres sikkert?
//
//        DogDTO doggy = GSON.fromJson(json, DogDTO.class);
//        doggy = dogFacade.createDog(doggy);
//        userFacade.addDogToUser(currentUser.getUserName(), doggy.getId());
//        return GSON.toJson(doggy);
        return "";
    }
}