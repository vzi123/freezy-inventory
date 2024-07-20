package freezy.events;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class OutwardCreatedPublisher {
    private final ApplicationEventPublisher eventPublisher;

    public OutwardCreatedPublisher(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    public void publishEvent(String adminUserId, Integer cost, String customerName) {
        OutwardCreatedEvent outwardCreatedEvent = new OutwardCreatedEvent(this, adminUserId, cost, customerName);
        eventPublisher.publishEvent(outwardCreatedEvent);
    }
}
