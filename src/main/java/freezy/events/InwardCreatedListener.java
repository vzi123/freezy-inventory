package freezy.events;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import freezy.entities.Quotation;
import freezy.entities.v1.UserV1;
import freezy.services.PdfGenerateService;
import freezy.services.QuotationService;
import freezy.services.v1.UserServiceV1;
import freezy.utils.Constants;
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
            String message = createMessageString(adminUser.getFirst_name(), inwardCreatedEvent.getCountOfItems().toString(),"inward_created" );
                   // "Hi " + adminUser.getFirst_name() + ", An Inward Inventory for " + inwardCreatedEvent.getCountOfItems() + " items has been created. Please check Freazy for more details.";
            freazyWhatsAppService.sendMessage(adminUser.getPhone_number(), message, Constants.INWARD_CREATED);
        }
        catch (Exception e){

        }
    }

    public String createMessageString(String userName, String inwardCount, String templateId){


        JsonObject template = new JsonObject();
//        template.add("variables", new JsonObject());
        template.addProperty("1", userName);
        template.addProperty("2", inwardCount);
        String templateString = template.toString();

        return templateString;
    }



}