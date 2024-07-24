package freezy.dto.v1;

import freezy.entities.v1.*;
import jakarta.persistence.*;

import java.util.List;

public class ConsignmentInfoDTO {
    private String id;
    private InventoryLogEntryV1 inOut;
    private String comments;
    private Integer itemCount;
    private String createdAt;
    private Integer totalAmount;
    private UserV1 createdFor;
    List<ProductV1> products;
    List<AccessoryV1> accessories;
    List<ServiceV1> services;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public InventoryLogEntryV1 getInOut() {
        return inOut;
    }

    public void setInOut(InventoryLogEntryV1 inOut) {
        this.inOut = inOut;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Integer getItemCount() {
        return itemCount;
    }

    public void setItemCount(Integer itemCount) {
        this.itemCount = itemCount;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Integer totalAmount) {
        this.totalAmount = totalAmount;
    }

    public UserV1 getCreatedFor() {
        return createdFor;
    }

    public void setCreatedFor(UserV1 createdFor) {
        this.createdFor = createdFor;
    }

    public List<ProductV1> getProducts() {
        return products;
    }

    public void setProducts(List<ProductV1> products) {
        this.products = products;
    }

    public List<AccessoryV1> getAccessories() {
        return accessories;
    }

    public void setAccessories(List<AccessoryV1> accessories) {
        this.accessories = accessories;
    }

    public List<ServiceV1> getServices() {
        return services;
    }

    public void setServices(List<ServiceV1> services) {
        this.services = services;
    }
}
