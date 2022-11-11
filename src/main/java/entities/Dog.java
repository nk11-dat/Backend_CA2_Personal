package entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

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
    private String life_span;

    @NotNull
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "weight_id_row", nullable = false)
    private Weight weight;

    public Dog() {
    }

    public Dog(Integer id, String name, String life_span) {
        this.id = id;
        this.name = name;
        this.life_span = life_span;
    }

    public Dog(Integer id, String name, String life_span, Weight weight) {
        this.id = id;
        this.name = name;
        this.life_span = life_span;
        this.weight = weight;
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

    public String getLife_span() {
        return life_span;
    }

    public void setLife_span(String life_span) {
        this.life_span = life_span;
    }

    public Weight getWeight() {
        return weight;
    }

    public void setWeight(Weight weightIdRow) {
        this.weight = weightIdRow;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Dog)) return false;
        Dog dog = (Dog) o;
        return Objects.equals(getId(), dog.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        return "Dog{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", lifeSpan='" + life_span + '\'' +
                ", weight=" + weight +
                '}';
    }
}