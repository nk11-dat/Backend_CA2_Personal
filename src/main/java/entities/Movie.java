package entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "movie")
public class Movie
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 255)
    @NotNull
    @Column(name = "Title", nullable = false)
    private String Title;

    @NotNull
    @Column(name = "Year", nullable = false)
    private Integer Year;

    @OneToMany(mappedBy = "movie")
    private List<UserRating> userRatings = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "movie_actors",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "actors_Actors"))
    private List<Actors> Actors = new ArrayList<>();

    public Movie() {
    }

    public Movie(String Title, Integer Year) {
        this.Title = Title;
        this.Year = Year;
    }

    public Movie(String Title, Integer Year, List<Actors> Actors) {
        this.Title = Title;
        this.Year = Year;
        this.Actors = Actors;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String Title) {
        this.Title = Title;
    }

    public Integer getYear() {
        return Year;
    }

    public void setYear(Integer Year) {
        this.Year = Year;
    }

    public List<UserRating> getUserRatings() {
        return userRatings;
    }

    public void setUserRatings(List<UserRating> userRatings) {
        this.userRatings = userRatings;
    }

    public List<Actors> getActors() {
        return Actors;
    }

    public void setActors(List<Actors> Actors) {
        this.Actors = Actors;
    }

}