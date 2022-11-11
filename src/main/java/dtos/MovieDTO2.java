package dtos;

import entities.Movie;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

/**
 * A DTO for the {@link entities.Movie} entity
 */
public class MovieDTO2 implements Serializable
{
    private final Integer id;
    @Size(max = 255)
    @NotNull
    private final String title;
    @NotNull
    private final Integer year;
    private final List<String> actors;

    public MovieDTO2(Integer id, String title, Integer year, List<String> actors) {
        this.id = id;
        this.title = title;
        this.year = year;
        this.actors = actors;
    }

    public MovieDTO2(Movie movie) {
        this.id = movie.getId();
        this.title = movie.getTitle();
        this.year = movie.getYear();
        this.actors = movie.getActors();
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

    public List<String> getActors() {
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
}