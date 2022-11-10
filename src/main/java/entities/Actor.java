package entities;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "actors")
public class Actor
{
    @Id
    @Size(max = 45)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "actor", nullable = false, length = 45)
    private String actorName;

    @ManyToMany
    @JoinTable(name = "movie_actors",
            joinColumns = @JoinColumn(name = "actors_actor"),
            inverseJoinColumns = @JoinColumn(name = "movie_id"))
    private Set<Movie> movies = new LinkedHashSet<>();

    public String getActorName() {
        return actorName;
    }

    public void setActorName(String actorName) {
        this.actorName = actorName;
    }

    public Set<Movie> getMovies() {
        return movies;
    }

    public void setMovies(Set<Movie> movies) {
        this.movies = movies;
    }

}