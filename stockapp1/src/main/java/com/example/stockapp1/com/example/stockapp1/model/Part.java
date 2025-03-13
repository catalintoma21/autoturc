package com.example.stockapp1.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "parts")
public class Part {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;
    
    @Column(nullable = false)
    private String carModel;
    
    @Column(nullable = false)
    private double price;
    
    @Column(nullable = false)
    private int euro;
    
    @Column(nullable = false)
    private String code;
    
    // Nou: indică dacă piesa face parte din configurația standard
    @Column(nullable = false)
    private boolean standard;

    public Part() {}

    // Constructor actualizat pentru a primi și flag-ul "standard"
    public Part(String name, String carModel, double price, int euro, String code, boolean standard) {
        this.name = name.trim();
        this.carModel = carModel.trim();
        this.price = price;
        this.euro = euro;
        this.code = code.trim();
        this.standard = standard;
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

    public boolean isStandard() {
        return standard;
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

    public void setStandard(boolean standard) {
        this.standard = standard;
    }
}
