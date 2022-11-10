package dtos;

import entities.Actor;
import entities.Movie;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * A DTO for the {@link Movie} entity
 */
public class MovieDTO implements Serializable
{
    private final Integer id;
    @Size(max = 255)
    @NotNull
    private final String title;
    @NotNull
    private final Integer year;
    private final List<ActorInnerDTO> actors = new ArrayList<>();

    public MovieDTO(Integer id, String title, Integer year) { //List<ActorInnerDTO> actors
        this.id = id;
        this.title = title;
        this.year = year;
//        this.actors = actors;
    }

    public MovieDTO(Movie movie) {
        this.id = movie.getId();
        this.title = movie.getTitle();
        this.year = movie.getYear();
        movie.getActors().forEach(actor -> {
            this.actors.add(new ActorInnerDTO(actor));
        });
    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Integer getYear() {
        return year;
    }

    public List<ActorInnerDTO> getActors() {
        return actors;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "title = " + title + ", " +
                "year = " + year + ", " +
                "actors = " + actors + ")";
    }

    public static List<MovieDTO> getDTOs(List<Movie> movies)
    {
        List<MovieDTO> movieDTOList = new ArrayList<>();
        movies.forEach(m -> movieDTOList.add(new MovieDTO(m)));
        return movieDTOList;
    }

    /**
     * A DTO for the {@link Actor} entity
     */
    public static class ActorInnerDTO implements Serializable
    {
        @Size(max = 45)
        private final String actorName;

        public ActorInnerDTO(String actorName) {
            this.actorName = actorName;
        }

        public ActorInnerDTO(Actor actor) {
            this.actorName = actor.getActorName();
        }

        public String getActorName() {
            return actorName;
        }

        @Override
        public String toString() {
            return getClass().getSimpleName() + "(" +
                    "actorName = " + actorName + ")";
        }
    }
}