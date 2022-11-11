package entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "cats")
public class Cat
{
    @Id
    @Size(max = 45)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, length = 45)
    private String id;

    @Size(max = 90)
    @Column(name = "name", length = 90)
    private String name;

    @Column(name = "indoor")
    private Integer indoor;

    @NotNull
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "weight_id_row", nullable = false)
    private Weight weight;

    @ManyToMany(mappedBy = "cats")
    private Set<User> users = new LinkedHashSet<>();

    public Cat() {
    }

    public Cat(String id, String name, Integer indoor) {
        this.id = id;
        this.name = name;
        this.indoor = indoor;
    }

    public Cat(String id, String name, Integer indoor, Weight weights) {
        this.id = id;
        this.name = name;
        this.indoor = indoor;
        this.weight = weights;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getIndoor() {
        return indoor;
    }

    public void setIndoor(Integer indoor) {
        this.indoor = indoor;
    }

    public Weight getWeight() {
        return weight;
    }

    public void setWeight(Weight weightIdRow) {
        this.weight = weightIdRow;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cat)) return false;
        Cat cat = (Cat) o;
        return Objects.equals(getId(), cat.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        return "Cat{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", indoor=" + indoor +
                ", weight=" + weight +
                '}';
    }
}