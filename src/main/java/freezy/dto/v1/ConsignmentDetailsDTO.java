package freezy.dto.v1;

import java.util.List;

public class ConsignmentDetailsDTO {
    String comments;
    String userId;
    List<GoodsDetailDTO> products;
    List<GoodsDetailDTO> accessories;
    List<GoodsDetailDTO> services;

    public List<GoodsDetailDTO> getProducts() {
        return products;
    }

    public void setProducts(List<GoodsDetailDTO> products) {
        this.products = products;
    }

    public List<GoodsDetailDTO> getAccessories() {
        return accessories;
    }

    public void setAccessories(List<GoodsDetailDTO> accessories) {
        this.accessories = accessories;
    }

    public List<GoodsDetailDTO> getServices() {
        return services;
    }

    public void setServices(List<GoodsDetailDTO> services) {
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
