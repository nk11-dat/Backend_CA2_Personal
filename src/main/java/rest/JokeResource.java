package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.ChuckDTO;
import dtos.CombinedJokeDTO;
import dtos.DadDTO;
import facades.MultiThreadFacade;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Path("/joke")
public class JokeResource
{
    MultiThreadFacade jf = new MultiThreadFacade();
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String demo() {
        return "{\"msg\":\"Hello, joke World\"}";
    }

    @Path("haha")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getJokes() throws ExecutionException, InterruptedException
    {
        List<String> URL = new ArrayList<>();
        URL.add("https://icanhazdadjoke.com/");
        URL.add("https://api.chucknorris.io/jokes/random");
        List<String> jokeJSON = jf.parallelRun(URL);
        DadDTO dad = GSON.fromJson(jokeJSON.get(0), DadDTO.class);
        ChuckDTO chuck = GSON.fromJson(jokeJSON.get(1), ChuckDTO.class);
        CombinedJokeDTO combi = new CombinedJokeDTO(dad,chuck);
        return GSON.toJson(combi);
    }

}

