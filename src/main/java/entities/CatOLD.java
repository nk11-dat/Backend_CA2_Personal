//package entities;
//
//import javax.persistence.*;
//import javax.validation.constraints.Size;
//import java.util.ArrayList;
//import java.util.LinkedHashSet;
//import java.util.List;
//
//@Entity
//@Table(name = "cats")
//public class Cat
//{
//    @Id
//    @Size(max = 45)
//    @Column(name = "id", nullable = false, length = 45)
//    private String id;
//
//    @Size(max = 90)
//    @Column(name = "name", length = 90)
//    private String name;
//
//    @Column(name = "indoor")
//    private Integer indoor;
//
//    @OneToOne(mappedBy = "cats")
//    private Weight weights;
//
//    public Cat() {
//    }
//
//    public Cat(String id, String name, Integer indoor) {
//        this.id = id;
//        this.name = name;
//        this.indoor = indoor;
//    }
//
//    public Cat(String id, String name, Integer indoor, Weight weights) {
//        this.id = id;
//        this.name = name;
//        this.indoor = indoor;
//        this.weights = weights;
//    }
//
//    public String getId() {
//        return id;
//    }
//
//    public void setId(String id) {
//        this.id = id;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public Integer getIndoor() {
//        return indoor;
//    }
//
//    public void setIndoor(Integer indoor) {
//        this.indoor = indoor;
//    }
//
//    public Weight getWeights() {
//        return weights;
//    }
//
//    public void setWeights(Weight weights) {
//        this.weights = weights;
//    }
//
//}