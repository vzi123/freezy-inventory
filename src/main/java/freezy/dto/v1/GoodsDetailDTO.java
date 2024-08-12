package freezy.dto.v1;

public class GoodsDetailDTO {

    String productId;
    Integer quantity;
    Integer subTotal;
    String iduSerialNo;
    String oduSerialNo;
    String type;
    String accessoryId;
    String serviceId;
    Double tax;
    String taxPercentage;
    Double totalAmount;

    public Integer getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(Integer subTotal) {
        this.subTotal = subTotal;
    }

    public String getTaxPercentage() {
        return taxPercentage;
    }

    public void setTaxPercentage(String taxPercentage) {
        this.taxPercentage = taxPercentage;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Double getTax() {
        return tax;
    }

    public void setTax(Double tax) {
        this.tax = tax;
    }

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
}
