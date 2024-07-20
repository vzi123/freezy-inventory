package freezy.events;

import com.google.gson.JsonObject;
import freezy.entities.v1.UserV1;
import freezy.services.v1.UserServiceV1;
import freezy.utils.Constants;
import freezy.utils.FreazyWhatsAppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class OutwardCreatedListener {

    @Autowired
    FreazyWhatsAppService freazyWhatsAppService;

    @Autowired
    UserServiceV1 userServiceV1;

    @EventListener
    public void handleEvent(OutwardCreatedEvent outwardCreatedEvent) {
        try{
            System.out.println("No. of Items - " + outwardCreatedEvent.getCustomerName());
            UserV1 adminUser = userServiceV1.getUserById(outwardCreatedEvent.getAdminUserId());
            String message = createMessageString(adminUser.getFirst_name(), outwardCreatedEvent.getCustomerName(), outwardCreatedEvent.getCost().toString(), Constants.OUTWARD_CREATE);
                    //"Hi " + adminUser.getFirst_name() + ", An Outward Entry for " + outwardCreatedEvent.getCustomerName() + " for an amount Rs." + outwardCreatedEvent.getCost() + "/- has been created. Please check Freazy for more details.";
            freazyWhatsAppService.sendMessage(adminUser.getPhone_number(), message, Constants.OUTWARD_CREATE);
        }
        catch (Exception e){

        }
    }

    public String createMessageString(String userName, String customerName, String cost, String templateId){


        JsonObject template = new JsonObject();
        template.addProperty("1", userName);
        template.addProperty("2", customerName);
        template.addProperty("3", cost);
        String templateString = template.toString();

        return templateString;
    }
}