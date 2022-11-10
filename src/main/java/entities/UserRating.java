package entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "user_ratings")
public class UserRating
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "movie_id", nullable = false)
    private Movie movie;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "users_user_name", nullable = false)
    private User usersUserName;

    @Column(name = "user_ratings")
    private Integer userRatings;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public User getUsersUserName() {
        return usersUserName;
    }

    public void setUsersUserName(User usersUserName) {
        this.usersUserName = usersUserName;
    }

    public Integer getUserRatings() {
        return userRatings;
    }

    public void setUserRatings(Integer userRatings) {
        this.userRatings = userRatings;
    }

}