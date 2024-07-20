package freezy.events;

import org.springframework.context.ApplicationEvent;

import java.util.List;

public class StockDetailsEvent extends ApplicationEvent {
    String adminUserId;
    List<String> header;
    List<List<String>> inventory;
    public StockDetailsEvent(Object source, String adminUserId, List<String> header, List<List<String>> inventory) {
        super(source);
        this.adminUserId = adminUserId;
        this.header = header;
        this.inventory = inventory;
    }

    public String getAdminUserId() {
        return adminUserId;
    }

    public List<String> getHeader() {
        return header;
    }

    public List<List<String>> getInventory() {
        return inventory;
    }
}
