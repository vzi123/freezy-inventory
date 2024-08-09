package freezy.dto;

import freezy.dto.v1.GoodsDetailDTO;

import java.util.List;

public class QuotationDTO {

    String userId;
    String userPersona;
    Integer budget;
    String projectId;
    String projectName;
    List<GoodsDetailDTO> products;
    List<GoodsDetailDTO> accessories;
    List<GoodsDetailDTO> services;
    String quotationId;
    String status;
    Float discount;

    public String getUserId() {
        return userId;
    }

    public String getUserPersona() {
        return userPersona;
    }

    public void setUserPersona(String userPersona) {
        this.userPersona = userPersona;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setDiscount(Float discount) {
        this.discount = discount;
    }

    public Integer getBudget() {
        return budget;
    }

    public Float getDiscount() {
        return discount;
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

    public void setUserId(String userId) {
        this.userId = userId;
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

    public String getQuotationId() {
        return quotationId;
    }

    public void setQuotationId(String quotationId) {
        this.quotationId = quotationId;
    }

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
}
