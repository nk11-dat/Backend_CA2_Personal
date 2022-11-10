package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.MovieDTO;
import facades.MovieFacade;
import utils.EMF_Creator;

import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

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
//    @Consumes({MediaType.APPLICATION_JSON})
//    @Produces({MediaType.APPLICATION_JSON})
//    public String addPerson(String input){
//        PersonDTO personDTO = GSON.fromJson(input, PersonDTO.class);
//        PersonDTO newPersonDTO = FACADE.createPerson(personDTO);
//        return GSON.toJson(newPersonDTO);
//    }

}