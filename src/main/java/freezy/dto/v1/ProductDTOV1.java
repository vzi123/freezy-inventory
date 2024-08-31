package freezy.dto.v1;

import freezy.utils.FreazyValidField;

public class ProductDTOV1 {

    @FreazyValidField(notBlank = true, message = "Product Name cannot be blank.")
    String name;
    @FreazyValidField(notBlank = true, message = "Product Description cannot be blank.")
    String description;
    @FreazyValidField(notBlank = true, message = "Category Details cannot be blank.")
    String categoryId;
    Integer cost;
    @FreazyValidField(alphanumeric = true, message = "HSN No should be alphanumeric.")
    String hsnNo;
    String brandId;

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    public String getHsnNo() {
        return hsnNo;
    }

    public void setHsnNo(String hsnNo) {
        this.hsnNo = hsnNo;
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

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }
}
