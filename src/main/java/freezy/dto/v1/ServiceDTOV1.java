package freezy.dto.v1;

import freezy.utils.FreazyValidField;

public class ServiceDTOV1 {

//    @FreazyValidField(notBlank = true, message = "Service Name cannot be blank.")
    String name;
//    @FreazyValidField(notBlank = true, message = "Service Description cannot be blank.")
    String description;
    Integer cost;
//    @FreazyValidField(notBlank = true, message = "Categroy Details cannot be blank.")
    String categoryId;
//    @FreazyValidField(notBlank = true, message = "Service Tier Details cannot be blank.")
    String serviceTierId;

    public String getServiceTierId() {
        return serviceTierId;
    }

    public void setServiceTierId(String serviceTierId) {
        this.serviceTierId = serviceTierId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }
}
