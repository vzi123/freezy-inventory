package freezy.events;

import freezy.entities.v1.UserV1;
import freezy.services.v1.UserServiceV1;
import freezy.utils.FreazyWhatsAppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class StockDetailsListener {

    @Autowired
    FreazyWhatsAppService freazyWhatsAppService;

    @Autowired
    UserServiceV1 userServiceV1;

    @EventListener
    public void handleEvent(StockDetailsEvent stockDetailsEvent) {
        try{
            System.out.println("Stovl - " + stockDetailsEvent.getCurrentStock());
            UserV1 adminUser = userServiceV1.getUserById(stockDetailsEvent.getAdminUserId());
            String message = "Hi " + adminUser.getFirst_name() + ", the latest stock of " + stockDetailsEvent.getInventoryName() + " is " + stockDetailsEvent.getCurrentStock() + ". Please check Freazy for more details.";
            freazyWhatsAppService.sendMessage(adminUser.getPhone_number(), message);
        }
        catch (Exception e){

        }
    }
}