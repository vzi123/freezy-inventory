package freezy.dto.v1;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import freezy.entities.Brand;
import freezy.entities.Category;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.util.List;

public class ProductDetailsDTO {
    private String id;
    private String name;
    private String description;
    private Integer cost;
    private String hsnNo;
    private Category category;
    private Brand brand;

    private List<String> idus;

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

    public String getHsnNo() {
        return hsnNo;
    }

    public void setHsnNo(String hsnNo) {
        this.hsnNo = hsnNo;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public List<String> getIdus() {
        return idus;
    }

    public void setIdus(List<String> idus) {
        this.idus = idus;
    }
}
