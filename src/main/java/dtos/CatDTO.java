package dtos;

import entities.Cat;
import entities.Weight;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * A DTO for the {@link entities.Cat} entity
 */
public class CatDTO implements Serializable
{
    @Size(max = 45)
    private final String id;
    @Size(max = 90)
    private final String name;
    private final Integer indoor;
    private final WeightInnerDTO weights;

    public CatDTO(String id, String name, Integer indoor, WeightInnerDTO weights) {
        this.id = id;
        this.name = name;
        this.indoor = indoor;
        this.weights = weights;
    }

    public CatDTO(Cat c) {
        this.id = c.getId();
        this.name = c.getName();
        this.indoor = c.getIndoor();
        this.weights = new WeightInnerDTO(c.getWeights());
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getIndoor() {
        return indoor;
    }

    public WeightInnerDTO getWeights() {
        return weights;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "name = " + name + ", " +
                "indoor = " + indoor + ", " +
                "weights = " + weights + ")";
    }

    public static List<CatDTO> getDTOs(List<Cat> movies)
    {
        List<CatDTO> movieDTOList = new ArrayList<>();
        movies.forEach(m -> movieDTOList.add(new CatDTO(m)));
        return movieDTOList;
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
        public String toString() {
            return getClass().getSimpleName() + "(" +
                    "id = " + id + ", " +
                    "imperial = " + imperial + ", " +
                    "metric = " + metric + ")";
        }
    }
}