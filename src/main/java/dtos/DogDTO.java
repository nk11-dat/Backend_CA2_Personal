package dtos;

import entities.Dog;
import entities.Weight;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A DTO for the {@link entities.Dog} entity
 */
public class DogDTO implements Serializable
{
    private final Integer id;
    @Size(max = 90)
    private final String name;
    @Size(max = 45)
    private final String life_span;
    @NotNull
    private final WeightInnerDTO weight;

    public DogDTO(Integer id, String name, String life_span, WeightInnerDTO weight) {
        this.id = id;
        this.name = name;
        this.life_span = life_span;
        this.weight = weight;
    }

    public DogDTO(Integer id, String name, String life_span) {
        this.id = id;
        this.name = name;
        this.life_span = life_span;
        this.weight = new WeightInnerDTO(0, "-1", "-1");
    }

    public DogDTO(Dog d) {
        this.id = d.getId();
        this.name = d.getName();
        this.life_span = d.getLife_span();
        this.weight = new DogDTO.WeightInnerDTO(d.getWeight());
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLife_span() {
        return life_span;
    }

    public WeightInnerDTO getWeight() {
        return weight;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DogDTO entity = (DogDTO) o;
        return Objects.equals(this.id, entity.id) &&
                Objects.equals(this.name, entity.name) &&
                Objects.equals(this.life_span, entity.life_span) &&
                Objects.equals(this.weight, entity.weight);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, life_span, weight);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "name = " + name + ", " +
                "lifeSpan = " + life_span + ", " +
                "weight = " + weight + ")";
    }

    public static List<DogDTO> getDTOs(List<Dog> dogs)
    {
        List<DogDTO> dogDTOList = new ArrayList<>();
        dogs.forEach(dog ->  dogDTOList.add(new DogDTO(dog)));
        return dogDTOList;
    }

    /**
     * A DTO for the {@link entities.Weight} entity
     */
    public static class WeightInnerDTO implements Serializable
    {
        private final Integer id;
        @Size(max = 45)
        @NotNull
        private final String imperial;
        @Size(max = 45)
        @NotNull
        private final String metric;

        public WeightInnerDTO(Integer id, String imperial, String metric) {
            this.id = id;
            this.imperial = imperial;
            this.metric = metric;
        }

        public WeightInnerDTO(Weight weight) {
            this.id = weight.getId();
            this.imperial = weight.getImperial();
            this.metric = weight.getMetric();
        }

        public Integer getId() {
            return id;
        }

        public String getImperial() {
            return imperial;
        }

        public String getMetric() {
            return metric;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            WeightInnerDTO entity = (WeightInnerDTO) o;
            return Objects.equals(this.id, entity.id) &&
                    Objects.equals(this.imperial, entity.imperial) &&
                    Objects.equals(this.metric, entity.metric);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, imperial, metric);
        }

        @Override
        public String toString() {
            return getClass().getSimpleName() + "(" +
                    "id = " + id + ", " +
                    "imperial = " + imperial + ", " +
                    "metric = " + metric + ")";
        }
    }
}