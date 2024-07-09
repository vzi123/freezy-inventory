package freezy.events;

import org.springframework.context.ApplicationEvent;

public class InwardCreatedEvent extends ApplicationEvent {
    String adminUserId;
    Integer countOfItems;
    public InwardCreatedEvent(Object source, String adminUserId, Integer countOfItems) {
        super(source);
        this.adminUserId = adminUserId;
        this.countOfItems = countOfItems;
    }

    public String getAdminUserId() {
        return adminUserId;
    }

    public Integer getCountOfItems() {
        return countOfItems;
    }
}
