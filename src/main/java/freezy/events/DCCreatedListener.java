package freezy.events;

import com.google.gson.JsonObject;
import freezy.entities.v1.UserV1;
import freezy.services.v1.UserServiceV1;
import freezy.utils.FreazyConstants;
import freezy.utils.FreazyWhatsAppService;
import freezy.utils.FreazyStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class DCCreatedListener {

    @Autowired
    FreazyWhatsAppService freazyWhatsAppService;

    @Autowired
    UserServiceV1 userServiceV1;

    @EventListener
    public void handleEvent(DCCreatedEvent dcCreatedEvent) {
        try{
            System.out.println("Customer Name - " + dcCreatedEvent.getCustomerName());
            UserV1 adminUser = userServiceV1.getUserById(dcCreatedEvent.getAdminUserId());
            String message = createMessageString(adminUser.getFirst_name(), dcCreatedEvent.getCustomerName());
                    //"Hi " + adminUser.getFirst_name() + ", An Outward Entry for " + outwardCreatedEvent.getCustomerName() + " for an amount Rs." + outwardCreatedEvent.getCost() + "/- has been created. Please check Freazy for more details.";
            freazyWhatsAppService.sendFile(adminUser.getPhone_number(), message, FreazyConstants.DC_PDF_SENT, dcCreatedEvent.getDcURL());
            //Hi {{1}}, a DC for customer {{2}} is generated. Please find the PDF attached. Please check Freazy for more details.
        }
        catch (Exception e){

        }
    }

    public String createMessageString(String userName, String customerName){


        JsonObject template = new JsonObject();
        template.addProperty("1", userName);
        template.addProperty("2", FreazyStringUtils.replaceSpaces(customerName));
        String templateString = template.toString();

        return templateString;
    }
}