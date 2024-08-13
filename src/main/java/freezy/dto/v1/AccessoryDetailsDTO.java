package freezy.dto.v1;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import freezy.entities.v1.BrandV1;
import freezy.entities.v1.CategoryV1;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

public class AccessoryDetailsDTO {

    private String id;

    private String name;

    private String description;

    private Integer cost;

    private CategoryV1 category;

    private BrandV1 brand;

    private Double amount;

    private Integer count;

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public CategoryV1 getCategory() {
        return category;
    }

    public void setCategory(CategoryV1 category) {
        this.category = category;
    }

    public BrandV1 getBrand() {
        return brand;
    }

    public void setBrand(BrandV1 brand) {
        this.brand = brand;
    }
}
