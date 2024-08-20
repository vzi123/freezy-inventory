package freezy.dto.v1;

public class InventoryDTOV1 {

    String productId;
    Integer quantity;
    Integer unitPrice;
    Double gstPercent;
    Double subTotal;
    String iduSerialNo;
    String oduSerialNo;
    String type;
    String accessoryId;
    String serviceId;

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAccessoryId() {
        return accessoryId;
    }

    public void setAccessoryId(String accessoryId) {
        this.accessoryId = accessoryId;
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
}
