package com.alevel.lesson10.shop.model.phone;

import com.alevel.lesson10.shop.model.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Phone extends Product {

    @Column
    private String model;
    @Column
    private Manufacturer manufacturer;

    @Transient
    private List<String> details;
    @Transient
    private LocalDateTime creatingDate;
    @Transient
    private String currency;
    @Transient
    private OperationSystem operationSystem;


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

    public Phone(String title, int count, long price, String model, Manufacturer manufacturer, LocalDateTime creatingDate, String currency, OperationSystem operationSystem) {
        super(title, count, price);
        this.model = model;
        this.manufacturer = manufacturer;
        this.creatingDate = creatingDate;
        this.currency = currency;
        this.operationSystem = operationSystem;
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

    public OperationSystem getOperatingSystem() {
        return operationSystem;
    }

    public void setOperatingSystem(OperationSystem operationSystem) {
        this.operationSystem = operationSystem;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Phone{");
        sb.append("model='").append(model).append('\'');
        sb.append(", manufacturer=").append(manufacturer);
        sb.append(", details=").append(details);
        sb.append(", creatingDate=").append(creatingDate);
        sb.append(", currency='").append(currency).append('\'');
        sb.append(", operatingSystem=").append(operationSystem);
        sb.append(", id='").append(id).append('\'');
        sb.append(", title='").append(title).append('\'');
        sb.append(", count=").append(count);
        sb.append(", price=").append(price);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Phone phone = (Phone) o;
        return Objects.equals(model, phone.model) && manufacturer == phone.manufacturer && Objects.equals(details, phone.details) && Objects.equals(creatingDate, phone.creatingDate) && Objects.equals(currency, phone.currency) && Objects.equals(operationSystem, phone.operationSystem);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), model, manufacturer, details, creatingDate, currency, operationSystem);
    }
}
