package freezy.dto.v1;

public class CashFlowV1 {
    Integer month;
    Integer year;
    String inOrOut;
    Double amount;
    ProductDTOV1 product;

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getInOrOut() {
        return inOrOut;
    }

    public void setInOrOut(String inOrOut) {
        this.inOrOut = inOrOut;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public ProductDTOV1 getProduct() {
        return product;
    }

    public void setProduct(ProductDTOV1 product) {
        this.product = product;
    }
}
