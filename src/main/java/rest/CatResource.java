package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.*;
import entities.Cat;
import entities.Dog;
import facades.CatsFacade;
import utils.EMF_Creator;
import utils.FetchParallelJSON;

import javax.persistence.EntityManagerFactory;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Path("/cat")
public class CatResource
{
    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();

    private static final CatsFacade facade =  CatsFacade.getInstance(EMF);

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
        List<CatDTO> catDTOList = facade.getAll();
        return GSON.toJson(catDTOList);
    }

    @GET
    @Path("breed/{breed}")
    @Produces(MediaType.APPLICATION_JSON)
    public String populate(@PathParam("breed") String breed) throws ExecutionException, InterruptedException {

        FetchParallelJSON fpJSON = new FetchParallelJSON();
        List<String> urls = new ArrayList<>();
        urls.add("https://api.thecatapi.com/v1/breeds/search?q=" +breed);
        List<String> jsonError = fpJSON.parallelRun(urls);
        String json = jsonError.get(0);
        System.out.println(json);
        json = json.replace("[", "");
        json = json.replace("]", "");

        Cat kitty = GSON.fromJson(json, Cat.class);
//        facade.populate(kitty);
        return GSON.toJson(kitty);
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

