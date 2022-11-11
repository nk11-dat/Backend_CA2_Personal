package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.MovieDTO;
import entities.Movie;
import facades.MovieFacade;
import utils.EMF_Creator;

import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Path("/movies")
public class MovieResource
{
    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();

    private static final MovieFacade facade =  MovieFacade.getInstance(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @GET
    @Produces("text/plain")
    public String hello()
    {
        return "Hello, Movie fans!";
    }

    @GET
    @Path("all")
    @Produces(MediaType.APPLICATION_JSON)
    public String allMovies() {
        List<MovieDTO> movieDTOList = facade.getAll();
        return GSON.toJson(movieDTOList);
    }

    @GET
    @Path("count")
    @Produces({MediaType.APPLICATION_JSON})
    public String getCount() {

        long count = facade.countAll();
        return "{\"count\":"+count+"}";
    }

    @GET
    @Path("populate")
    @Produces(MediaType.APPLICATION_JSON)
    public String populare(){
        facade.populate();
        return "{\"Message\": \"DB populated with test data\"}";
    }

//    @POST
//    @Path("create")
////    @Consumes({MediaType.APPLICATION_JSON})
//    @Produces({MediaType.APPLICATION_JSON})
////    public String addPerson(String input){
//    public String addPerson() throws ExecutionException, InterruptedException {
//        List<String> URL = new ArrayList<>();
//        URL.add("https://www.omdbapi.com/?apikey=49f41d3d&t=Die+Hard");
//        List<String> jokeJSON = facade.parallelRun(URL);
//        MovieDTO movieDTO = GSON.fromJson(jokeJSON.get(0), MovieDTO.class);
//        MovieDTO newMovieDTO = facade.createMovie(movieDTO);
//        return GSON.toJson(newMovieDTO);
//    }

    @Path("search")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getJokes() throws ExecutionException, InterruptedException
    {
        List<String> URL = new ArrayList<>();
        URL.add("https://www.omdbapi.com/?apikey=49f41d3d&t=Die+Hard");
        List<String> jokeJSON = facade.parallelRun(URL);
        System.out.println();
//        jokeJSON.set(0, jokeJSON.get(0).replace('T', 't'));
        Movie dad = GSON.fromJson(jokeJSON.get(0), Movie.class);
        MovieDTO test = new MovieDTO(dad);
        return GSON.toJson(test);
    }

}