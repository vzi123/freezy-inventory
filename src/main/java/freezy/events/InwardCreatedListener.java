package freezy.events;

import freezy.entities.Quotation;
import freezy.entities.v1.UserV1;
import freezy.services.PdfGenerateService;
import freezy.services.QuotationService;
import freezy.services.v1.UserServiceV1;
import freezy.utils.FreazyEmailService;
import freezy.utils.FreazyWhatsAppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class InwardCreatedListener {

    @Autowired
    FreazyWhatsAppService freazyWhatsAppService;

    @Autowired
    UserServiceV1 userServiceV1;

    @EventListener
    public void handleEvent(InwardCreatedEvent inwardCreatedEvent) {
        try{
            System.out.println("No. of Items - " + inwardCreatedEvent.getCountOfItems());
            UserV1 adminUser = userServiceV1.getUserById(inwardCreatedEvent.getAdminUserId());
            String message = "Hi " + adminUser.getFirst_name() + ", An Inward Inventory for " + inwardCreatedEvent.getCountOfItems() + " items has been created. Please check Freazy for more details.";
            freazyWhatsAppService.sendMessage(adminUser.getPhone_number(), message);
        }
        catch (Exception e){

        }
    }
}