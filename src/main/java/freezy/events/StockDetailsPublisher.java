package freezy.events;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class StockDetailsPublisher {
    private final ApplicationEventPublisher eventPublisher;

    public StockDetailsPublisher(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    public void publishEvent(String adminUserId, String inventoryName, Integer currentStock) {
        StockDetailsEvent stockDetailsEvent = new StockDetailsEvent(this, adminUserId, inventoryName, currentStock);
        eventPublisher.publishEvent(stockDetailsEvent);
    }
}
