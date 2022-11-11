package entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
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
    private String title;

    @NotNull
    @Column(name = "Year", nullable = false)
    private Integer year;

    @OneToMany(mappedBy = "movie")
    private List<UserRating> userRatings = new ArrayList<>();

    @ElementCollection
    private List<String> actors = new ArrayList<>();

    public Movie() {
    }

    public Movie(String title, Integer year, List<String> actors) {
        this.title = title;
        this.year = year;
        this.actors = actors;
    }

    public Movie(String title, Integer year, String actors) {
        this.title = title;
        this.year = year;
        this.actors = Arrays.stream(actors.split(", ")).toList();
    }

    public Movie(String title, Integer year, List<UserRating> userRatings, List<String> actors) {
        this.title = title;
        this.year = year;
        this.userRatings = userRatings;
        this.actors = actors;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public List<UserRating> getUserRatings() {
        return userRatings;
    }

    public void setUserRatings(List<UserRating> userRatings) {
        this.userRatings = userRatings;
    }

    public List<String> getActors() {
        return actors;
    }

    public void setActors(List<String> actors) {
        this.actors = actors;
    }

}