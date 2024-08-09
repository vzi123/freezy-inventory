package freezy.dto.v1;

import freezy.entities.*;

import java.util.List;

public class ConsignmentInfoDTO {
    private String id;
    private InventoryLogEntryV1 inOut;
    private String comments;
    private Integer itemCount;
    private String createdAt;
    private Integer totalAmount;
    private UserV1 createdFor;
    List<Product> products;
    List<Accessory> accessories;
    List<Service> services;

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

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public List<Accessory> getAccessories() {
        return accessories;
    }

    public void setAccessories(List<Accessory> accessories) {
        this.accessories = accessories;
    }

    public List<Service> getServices() {
        return services;
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }
}
