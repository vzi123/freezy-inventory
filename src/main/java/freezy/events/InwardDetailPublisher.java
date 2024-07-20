package freezy.events;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class InwardDetailPublisher {
    private final ApplicationEventPublisher eventPublisher;

    public InwardDetailPublisher(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    public void publishEvent(String adminUserId, Integer quantity, String productName) {
        InwardDetailEvent inwardDetailEvent = new InwardDetailEvent(this, adminUserId, quantity, productName);
        eventPublisher.publishEvent(inwardDetailEvent);
    }
}
