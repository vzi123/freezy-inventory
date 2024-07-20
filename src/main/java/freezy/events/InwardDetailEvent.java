package freezy.events;

import org.springframework.context.ApplicationEvent;

public class InwardDetailEvent extends ApplicationEvent {
    String adminUserId;
    Integer quantity;
    String productName;

    public Integer getQuantity() {
        return quantity;
    }

    public String getProductName() {
        return productName;
    }

    public InwardDetailEvent(Object source, String adminUserId, Integer quantity, String productName) {
        super(source);
        this.adminUserId = adminUserId;
        this.quantity = quantity;
        this.productName = productName;
    }

    public String getAdminUserId() {
        return adminUserId;
    }

}
