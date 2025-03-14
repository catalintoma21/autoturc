package com.example.stockapp.entity;

import javax.persistence.*;

@Entity
@Table(name = "stock")
public class StockEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String partName;
    private String carModel;
    private double price;
    private int quantity;
    private String entryDate;
    private int euro;
    private String code;

    public StockEntry() { }

    public StockEntry(String partName, String carModel, double price, int quantity, String entryDate, int euro, String code) {
        this.partName = partName.trim();
        this.carModel = carModel.trim();
        this.price = price;
        this.quantity = quantity;
        this.entryDate = entryDate;
        this.euro = euro;
        this.code = code.trim();
    }

    // Getteri și setteri

    public Long getId() { return id; }
    public String getPartName() { return partName; }
    public String getCarModel() { return carModel; }
    public double getPrice() { return price; }
    public int getQuantity() { return quantity; }
    public String getEntryDate() { return entryDate; }
    public int getEuro() { return euro; }
    public String getCode() { return code; }
    
    public void setId(Long id) { this.id = id; }
    public void setPartName(String partName) { this.partName = partName; }
    public void setCarModel(String carModel) { this.carModel = carModel; }
    public void setPrice(double price) { this.price = price; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public void setEntryDate(String entryDate) { this.entryDate = entryDate; }
    public void setEuro(int euro) { this.euro = euro; }
    public void setCode(String code) { this.code = code; }
}
