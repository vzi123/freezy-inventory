package freezy.events;

import com.google.gson.JsonObject;
import freezy.entities.v1.UserV1;
import freezy.services.v1.UserServiceV1;
import freezy.utils.Constants;
import freezy.utils.FreazyWhatsAppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class StockDetailsListener {

    @Autowired
    FreazyWhatsAppService freazyWhatsAppService;

    @Autowired
    UserServiceV1 userServiceV1;

    @EventListener
    public void handleEvent(StockDetailsEvent stockDetailsEvent) {
        try{
            System.out.println("Stovl - " + stockDetailsEvent);
            UserV1 adminUser = userServiceV1.getUserById(stockDetailsEvent.getAdminUserId());
            String message = createMessage(stockDetailsEvent.getHeader(), stockDetailsEvent.getInventory());
            freazyWhatsAppService.sendMessage(adminUser.getPhone_number(), message, Constants.STOCK_ALERT);
            //freazyWhatsAppService.sendStockMessage(adminUser.getPhone_number(),stockDetailsEvent.getHeader(), stockDetailsEvent.getInventory());
        }
        catch (Exception e){

        }
    }

    public String createMessage(List<String> headers, List<List<String>> data){


        List<List<String>> tableData = Stream.of(
                headers
        ).collect(Collectors.toList());
        tableData.addAll(data);

        // Convert the table data to a formatted string
        //String header = String.join("\t", tableData.get(0));
        String header = String.join("  ", tableData.get(0));
//        String tableContent = tableData.subList(1, tableData.size()).stream()
//                .map(row -> String.join("\t", row))
//                .collect(Collectors.joining("\n"));
        String tableContent = tableData.subList(1, tableData.size()).stream()
                .map(row -> String.join("  ", row))
                .collect(Collectors.joining("\\n"));
        JsonObject template = new JsonObject();
        template.addProperty("1", header);
        template.addProperty("2", tableContent);
        String templateString = template.toString();

        return templateString;
    }
}