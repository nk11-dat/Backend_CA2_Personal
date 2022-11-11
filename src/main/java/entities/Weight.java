package entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "weight")
public class Weight
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_row", nullable = false)
    private Integer id;

    @Size(max = 45)
    @NotNull
    @Column(name = "imperial", nullable = false, length = 45)
    private String imperial;

    @Size(max = 45)
    @NotNull
    @Column(name = "metric", nullable = false, length = 45)
    private String metric;

    @OneToOne(mappedBy = "weight")
    private Cat cat;

    @OneToOne(mappedBy = "weight")
    private Dog dog;

    public Weight() {
    }

    public Weight(String imperial, String metric) {
        this.imperial = imperial;
        this.metric = metric;
    }

    public Weight(Dog dog, String imperial, String metric) {
        this.dog = dog;
        this.imperial = imperial;
        this.metric = metric;
    }

    public Weight(Cat cat, String imperial, String metric) {
        this.cat = cat;
        this.imperial = imperial;
        this.metric = metric;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImperial() {
        return imperial;
    }

    public void setImperial(String imperial) {
        this.imperial = imperial;
    }

    public String getMetric() {
        return metric;
    }

    public void setMetric(String metric) {
        this.metric = metric;
    }

    public Cat getCat() {
        return cat;
    }

    public void setCat(Cat cat) {
        this.cat = cat;
    }

    public Dog getDog() {
        return dog;
    }

    public void setDog(Dog dog) {
        this.dog = dog;
    }

}