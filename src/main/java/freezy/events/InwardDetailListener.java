package freezy.events;

import com.google.gson.JsonObject;
import freezy.entities.v1.UserV1;
import freezy.services.v1.UserServiceV1;
import freezy.utils.FreazyConstants;
import freezy.utils.FreazyWhatsAppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class InwardDetailListener {

    @Autowired
    FreazyWhatsAppService freazyWhatsAppService;

    @Autowired
    UserServiceV1 userServiceV1;

    @EventListener
    public void handleEvent(InwardDetailEvent inwardDetailEvent) {
        try{
            System.out.println("No. of Items - " + inwardDetailEvent.getProductName());
            UserV1 adminUser = userServiceV1.getUserById(inwardDetailEvent.getAdminUserId());
            String message = createMessageString(adminUser.getFirst_name(), inwardDetailEvent.getQuantity().toString(), inwardDetailEvent.getProductName(), "inward_details" );
                    //"Hi " + adminUser.getFirst_name() + ", A procurement for " + inwardDetailEvent.getQuantity() + " #s for " + inwardDetailEvent.getProductName() + " is complete. Please check Freazy for more details.";
            freazyWhatsAppService.sendMessage(adminUser.getPhone_number(), message, FreazyConstants.INWARD_DETAILS);
        }
        catch (Exception e){

        }
    }

    public String createMessageString(String userName, String quantity, String productName, String templateId){


        JsonObject template = new JsonObject();
        template.addProperty("1", userName);
        template.addProperty("2", quantity);
        template.addProperty("3", productName);
        String templateString = template.toString();

        return templateString;
    }
}