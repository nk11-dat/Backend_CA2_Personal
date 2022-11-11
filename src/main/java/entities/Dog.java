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
    private String lifeSpan;

    @NotNull
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "weight_id_row", nullable = false)
    private Weight weight;

    public Dog() {
    }

    public Dog(Integer id, String name, String lifeSpan) {
        this.id = id;
        this.name = name;
        this.lifeSpan = lifeSpan;
    }

    public Dog(Integer id, String name, String lifeSpan, Weight weight) {
        this.id = id;
        this.name = name;
        this.lifeSpan = lifeSpan;
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

    public String getLifeSpan() {
        return lifeSpan;
    }

    public void setLifeSpan(String lifeSpan) {
        this.lifeSpan = lifeSpan;
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
                ", lifeSpan='" + lifeSpan + '\'' +
                ", weight=" + weight +
                '}';
    }
}