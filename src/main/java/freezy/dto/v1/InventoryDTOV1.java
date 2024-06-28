package freezy.dto.v1;

public class InventoryDTOV1 {

    String productId;
    Integer quantity;
    Integer unitPrice;
    String iduSerial;
    String oduSerial;

    public String getIduSerial() {
        return iduSerial;
    }

    public void setIduSerial(String iduSerial) {
        this.iduSerial = iduSerial;
    }

    public String getOduSerial() {
        return oduSerial;
    }

    public void setOduSerial(String oduSerial) {
        this.oduSerial = oduSerial;
    }

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
}
