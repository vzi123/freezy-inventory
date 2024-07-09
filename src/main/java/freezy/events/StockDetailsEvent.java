package freezy.events;

import org.springframework.context.ApplicationEvent;

public class StockDetailsEvent extends ApplicationEvent {
    String adminUserId;
    Integer currentStock;
    String inventoryName;
    public StockDetailsEvent(Object source, String adminUserId, String inventoryName, Integer currentStock) {
        super(source);
        this.adminUserId = adminUserId;
        this.inventoryName = inventoryName;
        this.currentStock = currentStock;
    }

    public String getAdminUserId() {
        return adminUserId;
    }

    public Integer getCurrentStock() {
        return currentStock;
    }

    public String getInventoryName() {
        return inventoryName;
    }
}
