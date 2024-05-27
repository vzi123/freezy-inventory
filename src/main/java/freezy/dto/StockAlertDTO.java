package freezy.dto;

public class StockAlertDTO {

    String productId;
    String productName;
    Integer alertQuantity;
    Integer currentQuantity;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getAlertQuantity() {
        return alertQuantity;
    }

    public void setAlertQuantity(Integer alertQuantity) {
        this.alertQuantity = alertQuantity;
    }

    public Integer getCurrentQuantity() {
        return currentQuantity;
    }

    public void setCurrentQuantity(Integer currentQuantity) {
        this.currentQuantity = currentQuantity;
    }
}
