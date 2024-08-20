package freezy.dto.v1;

import freezy.entities.v1.BrandV1;
import freezy.entities.v1.CategoryV1;

public class ProductDetailsDTO {
    private String id;
    private String name;
    private String description;
    private Integer cost;
    private String hsnNo;
    private CategoryV1 category;
    private BrandV1 brand;
    private Double amount;
    String iduSerialNo;
    String oduSerialNo;
    Double gstPercent;
    Double subTotal;
    Integer unitPrice;

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
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

    public String getHsnNo() {
        return hsnNo;
    }

    public void setHsnNo(String hsnNo) {
        this.hsnNo = hsnNo;
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

    public String getIduSerialNo() {
        return iduSerialNo;
    }

    public void setIduSerialNo(String iduSerialNo) {
        this.iduSerialNo = iduSerialNo;
    }

    public String getOduSerialNo() {
        return oduSerialNo;
    }

    public void setOduSerialNo(String oduSerialNo) {
        this.oduSerialNo = oduSerialNo;
    }

    public Double getGstPercent() {
        return gstPercent;
    }

    public void setGstPercent(Double gstPercent) {
        this.gstPercent = gstPercent;
    }

    public Double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(Double subTotal) {
        this.subTotal = subTotal;
    }

    public Integer getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Integer unitPrice) {
        this.unitPrice = unitPrice;
    }
}
