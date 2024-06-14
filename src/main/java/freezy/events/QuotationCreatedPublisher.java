package freezy.events;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class QuotationCreatedPublisher {
    private final ApplicationEventPublisher eventPublisher;

    public QuotationCreatedPublisher(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    public void publishEvent(String quotationId) {
        QuotationCreatedEvent quotationCreatedEvent = new QuotationCreatedEvent(this, quotationId);
        eventPublisher.publishEvent(quotationCreatedEvent);
    }
}
