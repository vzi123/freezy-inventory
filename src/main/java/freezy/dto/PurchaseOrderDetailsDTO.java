package freezy.dto;

import java.util.List;

public class PurchaseOrderDetailsDTO {

    String userId;
    String userPersona;
    Integer budget;
    String projectId;
    List<POItemsDTO> poItems;
    String poId;

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

    public Integer getBudget() {
        return budget;
    }

    public void setBudget(Integer budget) {
        this.budget = budget;
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
