package entities;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "dogs")
public class Dog
{
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 90)
    @Column(name = "name", length = 90)
    private String name;

    @Size(max = 45)
    @Column(name = "life_span", length = 45)
    private String lifeSpan;

    @OneToOne(mappedBy = "dogs")
    private Weight weights;

    public Dog() {
    }

    public Dog(Integer id, String name, String lifeSpan) {
        this.id = id;
        this.name = name;
        this.lifeSpan = lifeSpan;
    }

    public Dog(Integer id, String name, String lifeSpan, Weight weights) {
        this.id = id;
        this.name = name;
        this.lifeSpan = lifeSpan;
        this.weights = weights;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLifeSpan() {
        return lifeSpan;
    }

    public void setLifeSpan(String lifeSpan) {
        this.lifeSpan = lifeSpan;
    }

    public Weight getWeights() {
        return weights;
    }

    public void setWeights(Weight weights) {
        this.weights = weights;
    }

}