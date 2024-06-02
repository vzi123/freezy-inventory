package freezy.dto;

import java.util.List;

public class PurchaseOrderDetailsDTO {

    String userId;
    String userPersona;
    Integer discount;
    String projectId;
    List<POItemsDTO> poItems;
    String poId;

    String comments;

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public String getPoId() {
        return poId;
    }

    public void setPoId(String poId) {
        this.poId = poId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserPersona() {
        return userPersona;
    }

    public void setUserPersona(String userPersona) {
        this.userPersona = userPersona;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public List<POItemsDTO> getPoItems() {
        return poItems;
    }

    public void setPoItems(List<POItemsDTO> poItems) {
        this.poItems = poItems;
    }
}
