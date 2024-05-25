package freezy.dto;

import java.util.List;

public class QuotationDTO {

    String userId;
    String userPersona;
    Integer budget;
    String projectId;
    String projectName;
    List<QuotationItemsDTO> quotationItems;
    String quotationId;
    String status;

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public List<QuotationItemsDTO> getQuotationItems() {
        return quotationItems;
    }

    public void setQuotationItems(List<QuotationItemsDTO> quotationItems) {
        this.quotationItems = quotationItems;
    }

    public String getQuotationId() {
        return quotationId;
    }

    public void setQuotationId(String quotationId) {
        this.quotationId = quotationId;
    }
}
