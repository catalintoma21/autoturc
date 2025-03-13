package com.example.stockapp.entity;

import javax.persistence.*;

@Entity
@Table(name = "car_models")
public class CarModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String modelName;

    public CarModel() { }

    public CarModel(String modelName) {
        this.modelName = modelName.trim();
    }

    // Getteri și setteri
    public Long getId() { return id; }
    public String getModelName() { return modelName; }
    public void setId(Long id) { this.id = id; }
    public void setModelName(String modelName) { this.modelName = modelName; }
}
