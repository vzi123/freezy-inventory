package freezy.events;

import freezy.entities.Quotation;
import freezy.utils.FreazyPdfGenerateService;
import freezy.services.QuotationService;
import freezy.utils.FreazyEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class QuotationCreatedListener {

    @Autowired
    QuotationService quotationService;

    @Autowired
    FreazyEmailService freazyEmailService;

    @Autowired
    FreazyPdfGenerateService freazyPdfGenerateService;

    @EventListener
    public void handleEvent(QuotationCreatedEvent quotationCreatedEvent) {
        try{
            System.out.println("Quotation Id - " + quotationCreatedEvent.getQuotationId());
            Quotation quotation = quotationService.getQuotationById(quotationCreatedEvent.getQuotationId());
            File attachment = freazyPdfGenerateService.generateQuotation(quotation);
            freazyEmailService.sendEmail(attachment);
        }
        catch (Exception e){

        }
    }
}