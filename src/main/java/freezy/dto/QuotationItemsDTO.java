package freezy.dto;

public class QuotationItemsDTO {

    String productId;
    Integer quantity;
    Integer unitPrice;
    Integer discountAmount;
    Integer effectivePrice;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Integer unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Integer getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(Integer discountAmount) {
        this.discountAmount = discountAmount;
    }

    public Integer getEffectivePrice() {
        return effectivePrice;
    }

    public void setEffectivePrice(Integer effectivePrice) {
        this.effectivePrice = effectivePrice;
    }
}
