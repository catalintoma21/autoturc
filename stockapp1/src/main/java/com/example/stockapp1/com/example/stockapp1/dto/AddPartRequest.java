package com.example.stockapp1.dto;

public class AddPartRequest {
    private String name;
    private String carModel;
    private double price;
    private int euro;
    private String code;
    private boolean standard; // nou

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getCarModel() {
        return carModel;
    }
    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public int getEuro() {
        return euro;
    }
    public void setEuro(int euro) {
        this.euro = euro;
    }
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public boolean isStandard() {
        return standard;
    }
    public void setStandard(boolean standard) {
        this.standard = standard;
    }
}
