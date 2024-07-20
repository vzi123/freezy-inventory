package freezy.events;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StockDetailsPublisher {
    private final ApplicationEventPublisher eventPublisher;

    public StockDetailsPublisher(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    public void publishEvent(String adminUserId, List<String> headers, List<List<String>> inventory) {
        StockDetailsEvent stockDetailsEvent = new StockDetailsEvent(this, adminUserId, headers, inventory);
        eventPublisher.publishEvent(stockDetailsEvent);
    }
}
