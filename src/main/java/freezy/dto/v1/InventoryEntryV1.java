package freezy.dto.v1;

import freezy.utils.FreazyValidField;

import java.util.List;

public class InventoryEntryV1 {
    String comments;
    @FreazyValidField(notBlank = true, message = "User Details cannot be blank")
    String userId = "";
    List<InventoryDTOV1> products;
    List<InventoryDTOV1> accessories;
    List<InventoryDTOV1> services;

    public List<InventoryDTOV1> getProducts() {
        return products;
    }

    public void setProducts(List<InventoryDTOV1> products) {
        this.products = products;
    }

    public List<InventoryDTOV1> getAccessories() {
        return accessories;
    }

    public void setAccessories(List<InventoryDTOV1> accessories) {
        this.accessories = accessories;
    }

    public List<InventoryDTOV1> getServices() {
        return services;
    }

    public void setServices(List<InventoryDTOV1> services) {
        this.services = services;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
