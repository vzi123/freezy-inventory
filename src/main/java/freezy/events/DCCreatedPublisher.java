package freezy.events;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class DCCreatedPublisher {
    private final ApplicationEventPublisher eventPublisher;

    public DCCreatedPublisher(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    public void publishEvent(String adminUserId, String dcURL, String customerName) {
        DCCreatedEvent dcCreatedEvent = new DCCreatedEvent(this, adminUserId, dcURL, customerName);
        eventPublisher.publishEvent(dcCreatedEvent);
    }
}
