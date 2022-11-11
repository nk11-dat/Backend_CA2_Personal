//package entities;
//
//import javax.persistence.*;
//import javax.validation.constraints.NotNull;
//import javax.validation.constraints.Size;
//
//@Entity
//@Table(name = "weight")
//public class Weight
//{
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "id_row", nullable = false)
//    private Integer id;
//
//    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "cats_id")
//    private Cat cats;
//
//    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "dogs_id")
//    private Dog dogs;
//
//    @Size(max = 45)
//    @NotNull
//    @Column(name = "imperial", nullable = false, length = 45)
//    private String imperial;
//
//    @Size(max = 45)
//    @NotNull
//    @Column(name = "metric", nullable = false, length = 45)
//    private String metric;
//
//    public Weight() {
//    }
//
//    public Weight(Dog dogs, String imperial, String metric) {
//        this.dogs = dogs;
//        this.imperial = imperial;
//        this.metric = metric;
//    }
//
//    public Weight(Cat cats, String imperial, String metric) {
//        this.cats = cats;
//        this.imperial = imperial;
//        this.metric = metric;
//    }
//
//    public Integer getId() {
//        return id;
//    }
//
//    public void setId(Integer id) {
//        this.id = id;
//    }
//
//    public Cat getCat() {
//        return cats;
//    }
//
//    public void setCat(Cat cats) {
//        this.cats = cats;
//    }
//
//    public Dog getDogs() {
//        return dogs;
//    }
//
//    public void setDogs(Dog dogs) {
//        this.dogs = dogs;
//    }
//
//    public String getImperial() {
//        return imperial;
//    }
//
//    public void setImperial(String imperial) {
//        this.imperial = imperial;
//    }
//
//    public String getMetric() {
//        return metric;
//    }
//
//    public void setMetric(String metric) {
//        this.metric = metric;
//    }
//
//}