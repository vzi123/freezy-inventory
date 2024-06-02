package freezy.dto;

import java.util.List;

public class POPostDTO {

    Integer userId;
    Integer projectId;
    Integer discount;
    List<POItemsDTO> poItems;
    String status;
    String userPersona;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public List<POItemsDTO> getPoItems() {
        return poItems;
    }

    public void setPoItems(List<POItemsDTO> poItems) {
        this.poItems = poItems;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserPersona() {
        return userPersona;
    }

    public void setUserPersona(String userPersona) {
        this.userPersona = userPersona;
    }
}
