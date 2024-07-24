package freezy.events;

import org.springframework.context.ApplicationEvent;

public class DCCreatedEvent extends ApplicationEvent {
    String adminUserId;
    String dcURL;
    String customerName;
    public DCCreatedEvent(Object source, String adminUserId, String dcURL, String customerName) {
        super(source);
        this.adminUserId = adminUserId;
        this.dcURL = dcURL;
        this.customerName = customerName;
    }

    public String getAdminUserId() {
        return adminUserId;
    }

    public String getDcURL() {
        return dcURL;
    }

    public String getCustomerName() {
        return customerName;
    }
}
