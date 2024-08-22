package freezy.dto.v1;

public class InventoryDTOV1 {

    String productId = "";
    String product = "";
    String description = "";
    Integer quantity = 0;
    Integer unitPrice = 0;
    Integer discountAmount = 0;
    Double subTotal = 0.0;
    Integer effectivePrice = 0;
    GSTDTO gstValue;
    String iduSerialNo = "";
    String oduSerialNo = "";
    String type = "";
    String accessoryId = "";
    String accessory = "";
    String serviceId = "";
    String service = "";
    Double gstPercent = 0.0;

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

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getAccessory() {
        return accessory;
    }

    public void setAccessory(String accessory) {
        this.accessory = accessory;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public GSTDTO getGstValue() {
        return gstValue;
    }

    public void setGstValue(GSTDTO gstValue) {
        this.gstValue = gstValue;
    }
}
