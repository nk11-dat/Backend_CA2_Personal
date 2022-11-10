package entities;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "actors")
public class Actors
{
    @Id
    @Size(max = 45)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Actors", nullable = false, length = 45)
    private String actorName;

    @ManyToMany
    @JoinTable(name = "movie_actors",
            joinColumns = @JoinColumn(name = "actors_Actors"),
            inverseJoinColumns = @JoinColumn(name = "movie_id"))
    private List<Movie> movies = new ArrayList<>();

    public Actors() {
    }

    public Actors(String actorName) {
        this.actorName = actorName;
    }

    public Actors(String actorName, List<Movie> movies) {
        this.actorName = actorName;
        this.movies = movies;
    }

    public String getActorName() {
        return actorName;
    }

    public void setActorName(String actorName) {
        this.actorName = actorName;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }

}