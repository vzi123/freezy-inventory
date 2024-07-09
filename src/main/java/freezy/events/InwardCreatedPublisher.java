package freezy.events;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class InwardCreatedPublisher {
    private final ApplicationEventPublisher eventPublisher;

    public InwardCreatedPublisher(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    public void publishEvent(String adminUserId, Integer countOfItems) {
        InwardCreatedEvent inwardCreatedEvent = new InwardCreatedEvent(this, adminUserId, countOfItems);
        eventPublisher.publishEvent(inwardCreatedEvent);
    }
}
