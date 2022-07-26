package com.alevel.lesson10.shop.model.phone;

import com.alevel.lesson10.shop.model.Product;

import java.time.LocalDateTime;
import java.util.List;

public class Phone extends Product {

    private String model;
    private Manufacturer manufacturer;
    private List<String> details;
    private LocalDateTime creatingDate;
    private String currency;
    private OperatingSystem operatingSystem;


    public Phone(String title, int count, long price, String model, Manufacturer manufacturer) {
        super(title, count, price);
        this.model = model;
        this.manufacturer = manufacturer;
    }

    public Phone(String title, int count, long price, String model, Manufacturer manufacturer, List<String> details) {
        super(title, count, price);
        this.model = model;
        this.manufacturer = manufacturer;
        this.details = details;
    }

    public Phone(String title, int count, long price, String model, Manufacturer manufacturer, LocalDateTime creatingDate, String currency, OperatingSystem operatingSystem) {
        super(title, count, price);
        this.model = model;
        this.manufacturer = manufacturer;
        this.creatingDate = creatingDate;
        this.currency = currency;
        this.operatingSystem = operatingSystem;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Manufacturer getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(Manufacturer manufacturer) {
        this.manufacturer = manufacturer;
    }

    public List<String> getDetails() {
        return details;
    }

    public void setDetails(List<String> details) {
        this.details = details;
    }

    public LocalDateTime getCreatingDate() {
        return creatingDate;
    }

    public void setCreatingDate(LocalDateTime creatingDate) {
        this.creatingDate = creatingDate;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public OperatingSystem getOperatingSystem() {
        return operatingSystem;
    }

    public void setOperatingSystem(OperatingSystem operatingSystem) {
        this.operatingSystem = operatingSystem;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Phone{");
        sb.append("model='").append(model).append('\'');
        sb.append(", manufacturer=").append(manufacturer);
        sb.append(", details=").append(details);
        sb.append(", creatingDate=").append(creatingDate);
        sb.append(", currency='").append(currency).append('\'');
        sb.append(", operatingSystem=").append(operatingSystem);
        sb.append(", id='").append(id).append('\'');
        sb.append(", title='").append(title).append('\'');
        sb.append(", count=").append(count);
        sb.append(", price=").append(price);
        sb.append('}');
        return sb.toString();
    }
}
