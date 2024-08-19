package freezy.schedule;

import freezy.dto.v1.InventoryListV1;
import freezy.entities.v1.InventoryTypeV1;
import freezy.entities.v1.UserV1;
import freezy.events.StockDetailsPublisher;
import freezy.services.v1.InventoryServiceV1;
import freezy.utils.FreazyStringUtils;
import freezy.utils.UtilsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class StockAlertSchedule {

    @Autowired
    InventoryServiceV1 inventoryServiceV1;

    @Autowired
    StockDetailsPublisher stockDetailsPublisher;

    @Autowired
    UtilsService utilsService;



    @Scheduled(cron = "0 0 0 * * ?", zone = "Asia/Kolkata")
    public void runEvey5Minutes() {
        System.out.println(" Here to calculate stock");
        List<InventoryListV1> stockDetails = inventoryServiceV1.getAllInventory();
        UserV1 superUser = utilsService.getSuperUserV1();
        List<String> headers = Arrays.asList(new String[] { "Product_Name", "Stock" });
        List<List<String>> inventory = new ArrayList<>();
        for(InventoryListV1 item: stockDetails){
            if(null != item){
                if(item.getType().equalsIgnoreCase(InventoryTypeV1.PRODUCT.name())){
                    List<String> items = new ArrayList<>();
                    items.add(FreazyStringUtils.replaceSpaces(item.getProduct().getName()));
                    items.add(item.getInventory().toString());
                    inventory.add(items);
//                    stockDetailsPublisher.publishEvent(superUser.getId(), item.getProduct().getName(), item.getInventory());
                }
                if(item.getType().equalsIgnoreCase(InventoryTypeV1.ACCESSORY.name())){
                    List<String> items = new ArrayList<>();
                    items.add(FreazyStringUtils.replaceSpaces(item.getAccessory().getName()));
                    items.add(item.getInventory().toString());
                    inventory.add(items);
//                    stockDetailsPublisher.publishEvent(superUser.getId(), item.getAccessory().getName(), item.getInventory());
                }
            }
        }
        stockDetailsPublisher.publishEvent(superUser.getId(), headers, inventory);
        System.out.println("Current time is :: " + LocalDate.now());
        System.out.println("Header is :: " + headers);

    }

    //stock alert email
    //payables email
    //receivables email
    //inventory log report



}
