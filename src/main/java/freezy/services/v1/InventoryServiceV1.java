package freezy.services.v1;


import freezy.dto.InventoryCountDTO;
import freezy.dto.InventoryDTO;
import freezy.dto.v1.InventoryDTOV1;
import freezy.dto.v1.InventoryEntryV1;
import freezy.entities.Product;
import freezy.entities.v1.InventoryLogEntryV1;
import freezy.entities.v1.InventoryLogV1;
import freezy.entities.v1.InventoryV1;
import freezy.entities.v1.ProductV1;
import freezy.repository.v1.InventoryRepositoryV1;
import freezy.utils.Constants;
import freezy.utils.UtilsService;
import org.springframework.beans.factory.annotation.Autowired;
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

    public List<InventoryV1> getAllInventory() {
        return inventoryRepositoryV1.findAllByOrderByIdAsc();
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
                    inventory.setInventory(((null != inventory.getInventory())?inventory.getInventory():0) + inventoryDTO.getStock());
                }
                else{
                    inventory.setInventory(((null != inventory.getInventory())?inventory.getInventory():0) - inventoryDTO.getStock());
                }
                inventory.setUpdatedAt(utilsService.generateDateFormat());
                inventory.setUpdatedBy(utilsService.getSuperUserV1());

                inventoryRepositoryV1.saveAndFlush(inventory);

                InventoryLogV1 inventoryLog = new InventoryLogV1();
                inventoryLog.setInventory(inventory);
                inventoryLog.setId(utilsService.generateId(Constants.INVENTORY_ORDER_PREFIX));
                inventoryLog.setAmount(inventoryDTO.getAmount());
                if(inOrOut.equalsIgnoreCase(Constants.INVENTORY_INC)){
                    inventoryLog.setInOut(InventoryLogEntryV1.IN);
                    inventoryLog.setComments("Procured " + inventoryDTO.getStock() + " on " + utilsService.generateDateFormat());
                }
                else{
                    inventoryLog.setInOut(InventoryLogEntryV1.OUT);
                    inventoryLog.setComments("Deducted " + inventoryDTO.getStock() + " on " + utilsService.generateDateFormat() + " for " + inventoryEntryV1.getComments());
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
