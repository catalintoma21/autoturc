package com.example.stockapp.entity;

import javax.persistence.*;

@Entity
@Table(name = "parts")
public class Part {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String carModel;
    private double price;
    private int euro;
    private String code;

    public Part() { }

    public Part(String name, String carModel, double price, int euro, String code) {
        this.name = name.trim();
        this.carModel = carModel.trim();
        this.price = price;
        this.euro = euro;
        this.code = code.trim();
    }

    // Getteri și setteri

    public Long getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getCarModel() {
        return carModel;
    }
    public double getPrice() {
        return price;
    }
    public int getEuro() {
        return euro;
    }
    public String getCode() {
        return code;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public void setEuro(int euro) {
        this.euro = euro;
    }
    public void setCode(String code) {
        this.code = code;
    }
}
