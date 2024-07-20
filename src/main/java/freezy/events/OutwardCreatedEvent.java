package freezy.events;

import org.springframework.context.ApplicationEvent;

public class OutwardCreatedEvent extends ApplicationEvent {
    String adminUserId;
    Integer cost;
    String customerName;
    public OutwardCreatedEvent(Object source, String adminUserId, Integer cost, String customerName) {
        super(source);
        this.adminUserId = adminUserId;
        this.cost = cost;
        this.customerName = customerName;
    }

    public String getAdminUserId() {
        return adminUserId;
    }

    public Integer getCost() {
        return cost;
    }

    public String getCustomerName() {
        return customerName;
    }
}
