package com.example.stockapp1.dto;

public class AddManualStockRequest {
    private String partName;
    private String carModel;
    private double price;
    private int quantity;
    private int euro;
    private String code;

    // Getteri și setteri
    public String getPartName() {
        return partName;
    }
    public void setPartName(String partName) {
        this.partName = partName;
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
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
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
}
