package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.ChuckDTO;
import dtos.CombinedJokeDTO;
import dtos.DadDTO;
import facades.Facade;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Path("/cat")
public class CatResource
{
    Facade facade = new Facade();
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String demo() {
        return "{\"msg\":\"Hello, joke World\"}";
    }

    @Path("info")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getJokes() throws ExecutionException, InterruptedException
    {
        List<String> URL = new ArrayList<>();
        URL.add("https://api.thecatapi.com/v1/images/search"); //Random cat img
        URL.add("https://catfact.ninja/fact"); //Random cat fact
        List<String> catJSON = facade.parallelRun(URL);
        DadDTO dad = GSON.fromJson(catJSON.get(0), DadDTO.class);
        ChuckDTO chuck = GSON.fromJson(catJSON.get(1), ChuckDTO.class);
        CombinedJokeDTO combi = new CombinedJokeDTO(dad,chuck);
        return GSON.toJson(combi);
    }

}

