package freezy.services.v1;


import freezy.dto.InventoryCountDTO;
import freezy.dto.InventoryDTO;
import freezy.dto.v1.InventoryDTOV1;
import freezy.dto.v1.InventoryEntryV1;
import freezy.dto.v1.InventoryListV1;
import freezy.entities.Product;
import freezy.entities.v1.*;
import freezy.repository.v1.InventoryRepositoryV1;
import freezy.utils.Constants;
import freezy.utils.UtilsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class InventoryServiceV1 {
    @Autowired
    private InventoryRepositoryV1 inventoryRepositoryV1;

    @Autowired
    InventoryLogServiceV1 inventoryLogServiceV1;

    @Autowired
    UtilsService utilsService;

    @Autowired
    ProductServiceV1 productServiceV1;

    @Autowired
    UserServiceV1 userServiceV1;

    @Autowired
    CategoryUOMMapServiceV1 categoryUOMMapServiceV1;

    public List<InventoryListV1> getAllInventory() {

        List<InventoryV1> inventoryV1s = inventoryRepositoryV1.findAll();
        List<InventoryListV1> inventories = new ArrayList<>();
        for(InventoryV1 v1: inventoryV1s){
            if(v1.getProduct() != null){
                InventoryListV1 listV1 = new InventoryListV1();
                listV1.setInventory(v1.getInventory());
                listV1.setId(v1.getId());
                listV1.setProduct(v1.getProduct());
                CategoryUOMMapV1 categoryUOMMapV1 = categoryUOMMapServiceV1.getUOMByCategory(v1.getProduct().getCategory().getId());
                if(!categoryUOMMapV1.getMultiple().equalsIgnoreCase("1")){
                    listV1.setUom(categoryUOMMapV1.getMultiple() + " " + categoryUOMMapV1.getUomv1().name());
                }
                else{
                    listV1.setUom(categoryUOMMapV1.getUomv1().name());
                }
                inventories.add(listV1);
            }
        }
        return inventories;
    }

    public InventoryV1 getInventoryById(String id) {
        return inventoryRepositoryV1.findById(id).orElse(null);
    }

    public void saveInventory(InventoryDTO inventoryDTO) {

        ProductV1 product = productServiceV1.getProductV1ById(inventoryDTO.getProductId());
        InventoryV1 inventory = inventoryRepositoryV1.findByProduct(product);
        if(null == inventory){
            inventory = new InventoryV1();
            inventory.setId(utilsService.generateId(Constants.INVENTORY_ORDER_PREFIX));
            inventory.setInventory(inventoryDTO.getStock());
        }
        else{
            inventory.setInventory(inventory.getInventory() + inventoryDTO.getStock());
        }
        inventory.setCreatedAt(utilsService.generateDateFormat());
        inventory.setCreatedBy(utilsService.getSuperUserV1());
        inventory.setProduct(product);
        inventory.setUpdatedAt(utilsService.generateDateFormat());
        inventory.setUpdatedBy(utilsService.getSuperUserV1());

        inventoryRepositoryV1.saveAndFlush(inventory);

        InventoryLogV1 inventoryLog = new InventoryLogV1();
        inventoryLog.setInventory(inventory);
        inventoryLog.setId(utilsService.generateId(Constants.INVENTORY_ORDER_PREFIX));
        inventoryLog.setInOut(InventoryLogEntryV1.IN);
        inventoryLog.setComments("Procured " + inventoryDTO.getStock() + " on " + utilsService.generateDateFormat());
        inventoryLog.setCreatedAt(utilsService.generateDateFormat());
        inventoryLogServiceV1.saveInventoryLog(inventoryLog);
    }

    public void deleteInventory(String id) {
        inventoryRepositoryV1.deleteById(id);
    }

    public List<InventoryCountDTO> getInventoryCount(){
        List<InventoryV1> inventories = inventoryRepositoryV1.findAll();
        List<InventoryCountDTO> countList = new ArrayList<InventoryCountDTO>();
        for (InventoryV1 inventory: inventories) {
            InventoryCountDTO dto = new InventoryCountDTO();
            dto.setId(inventory.getId());
            dto.setProductId(inventory.getProduct().getId());
            dto.setName(inventory.getProduct().getName());
            dto.setCount(inventory.getInventory());
            countList.add(dto);
        }
        return countList;
    }

    public void incrementOrDecrementInventory(InventoryEntryV1 inventoryEntryV1, String inOrOut){
//        try{
        if(null != inventoryEntryV1 && inventoryEntryV1.getInventories().size() > 0){
            for(InventoryDTOV1 inventoryDTO : inventoryEntryV1.getInventories()){
                ProductV1 product = productServiceV1.getProductV1ById(inventoryDTO.getProductId());
                InventoryV1 inventory = inventoryRepositoryV1.findByProduct(product);
                if(null == inventory){
                    inventory = new InventoryV1();
                    inventory.setId(utilsService.generateId(Constants.INVENTORY_ORDER_PREFIX));
                }
                inventory.setCreatedAt(utilsService.generateDateFormat());
                inventory.setCreatedBy(userServiceV1.getUserById(inventoryEntryV1.getUserId()));
                inventory.setProduct(product);
                if(inOrOut.equalsIgnoreCase(Constants.INVENTORY_INC)){
                    inventory.setInventory(((null != inventory.getInventory())?inventory.getInventory():0) + inventoryDTO.getQuantity());
                }
                else{
                    inventory.setInventory(((null != inventory.getInventory())?inventory.getInventory():0) - inventoryDTO.getQuantity());
                }
                inventory.setUpdatedAt(utilsService.generateDateFormat());
                inventory.setUpdatedBy(utilsService.getSuperUserV1());

                inventoryRepositoryV1.saveAndFlush(inventory);

                InventoryLogV1 inventoryLog = new InventoryLogV1();
                UserV1 user = userServiceV1.getUserById(inventoryEntryV1.getUserId());
                String userFirstName = (null != user.getFirst_name())?user.getFirst_name():" ";
                String userLastName = " ";
                inventoryLog.setInventory(inventory);
                inventoryLog.setId(utilsService.generateId(Constants.INVENTORY_ORDER_PREFIX));
                inventoryLog.setAmount(inventoryDTO.getUnitPrice());
                inventoryLog.setQuantity(inventoryDTO.getQuantity());
                inventoryLog.setUpdatedStock(inventory.getInventory());
                if(inOrOut.equalsIgnoreCase(Constants.INVENTORY_INC)){
                    inventoryLog.setInOut(InventoryLogEntryV1.IN);
                    inventoryLog.setComments("Procured " + inventoryDTO.getQuantity() + " on " + utilsService.generateDateFormat() + " from " +
                            userFirstName + " " + userLastName + " , Comments: " + inventoryEntryV1.getComments());
                }
                else{
                    inventoryLog.setInOut(InventoryLogEntryV1.OUT);
                    inventoryLog.setComments("Delivered " + inventoryDTO.getQuantity() + " on " + utilsService.generateDateFormat() + " for " +
                            userFirstName + " " + userLastName + " , Comments: " + inventoryEntryV1.getComments());
                }

                inventoryLog.setCreatedAt(utilsService.generateDateFormat());
                inventoryLogServiceV1.saveInventoryLog(inventoryLog);
            }
        }

//        }
//        catch (Exception e){
//
//        }
    }
}
