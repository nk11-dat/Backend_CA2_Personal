package entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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
    private Weight weightIdRow;

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

    public Weight getWeightIdRow() {
        return weightIdRow;
    }

    public void setWeightIdRow(Weight weightIdRow) {
        this.weightIdRow = weightIdRow;
    }

}