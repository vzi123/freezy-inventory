package freezy.events;

import org.springframework.context.ApplicationEvent;

public class QuotationCreatedEvent extends ApplicationEvent {
    String quotationId;
    public QuotationCreatedEvent(Object source, String quotationId) {
        super(source);
        this.quotationId = quotationId;
    }

    public String getQuotationId(){
        return this.quotationId;
    }
}
