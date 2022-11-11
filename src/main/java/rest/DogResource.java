package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.*;
import entities.User;
import facades.DogsFacade;
import facades.UserFacade;
import utils.EMF_Creator;
import utils.FetchParallelJSON;

import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Path("/dog")
public class DogResource
{
    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();

    private static final DogsFacade dogFacade = DogsFacade.getInstance(EMF);
    private static final UserFacade userFacade = UserFacade.getUserFacade(EMF);

    //    CatsFacade facade = new CatsFacade();
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String demo() {
        return "{\"msg\":\"Hello, cat World\"}";
    }

    @GET
    @Path("all")
    @Produces(MediaType.APPLICATION_JSON)
    public String allMovies() {
        List<DogDTO> dogDTOList = dogFacade.getAll();
        return GSON.toJson(dogDTOList);
    }

    @GET
    @Path("breed/{breed}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getByBreed(@PathParam("breed") String breed) throws ExecutionException, InterruptedException {

        FetchParallelJSON fpJSON = new FetchParallelJSON();
        List<String> urls = new ArrayList<>();
        urls.add("https://api.thedogapi.com/v1/breeds/search?q=" + breed);
        List<String> jsonError = fpJSON.parallelRun(urls);
        String json = jsonError.get(0);
        System.out.println(json);
        json = json.replace("[", "");
        json = json.replace("]", "");

        DogDTO doggy = GSON.fromJson(json, DogDTO.class);
//        doggy = dogFacade.createDog(doggy); //this creates/finds a dog in the DB
        return GSON.toJson(doggy);
    }

    @POST
    @Path("addDogToUser/{userName}")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces(MediaType.APPLICATION_JSON)
    public String addDog(@PathParam("userName")String userName, String input) {
        //Get Username... få det fra PathParam?
        User currentUser = userFacade.fiedUser(userName); //TODO: Hvordan kan dette gøres sikkert?

        //create dog and weight
        String json = input;
        System.out.println(json);
        json = json.replace("[", "");
        json = json.replace("]", "");

        DogDTO doggy = GSON.fromJson(json, DogDTO.class);
        doggy = dogFacade.createDog(doggy);
        userFacade.addDogToUser(currentUser.getUserName(), doggy.getId());
        return GSON.toJson(doggy);
    }

    @DELETE
    @Path("removeDogFromUser/{userName}/{dogId}")
    @Produces(MediaType.APPLICATION_JSON)
    public String removeDog(@PathParam("userName")String userName, @PathParam("dogId")int dogId) {
        DogDTO doggy = userFacade.removeDogFromUser(userName, dogId);
        return GSON.toJson(doggy);
    }

//    @Path("info")
//    @GET
//    @Produces(MediaType.APPLICATION_JSON)
//    public String getJokes() throws ExecutionException, InterruptedException
//    {
//        List<String> URL = new ArrayList<>();
//        URL.add("https://api.thecatapi.com/v1/images/search"); //Random cat img
//        URL.add("https://catfact.ninja/fact"); //Random cat fact
//        List<String> catJSON = facade.parallelRun(URL);
//        DadDTO dad = GSON.fromJson(catJSON.get(0), DadDTO.class);
//        ChuckDTO chuck = GSON.fromJson(catJSON.get(1), ChuckDTO.class);
//        CombinedJokeDTO combi = new CombinedJokeDTO(dad,chuck);
//        return GSON.toJson(combi);
//    }

}

