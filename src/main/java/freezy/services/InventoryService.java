package freezy.services;


import freezy.dto.InventoryCountDTO;
import freezy.dto.InventoryDTO;
import freezy.entities.Inventory;
import freezy.entities.InventoryLog;
import freezy.entities.InventoryLogEntry;
import freezy.entities.Product;
import freezy.repository.InventoryRepository;
import freezy.utils.Constants;
import freezy.utils.UtilsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class InventoryService {
    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    InventoryLogService inventoryLogService;

    @Autowired
    UtilsService utilsService;

    @Autowired
    ProductService productService;

    public List<Inventory> getAllInventory() {
        return inventoryRepository.findAll();
    }

    public Inventory getInventoryById(String id) {
        return inventoryRepository.findById(id).orElse(null);
    }

    public void saveInventory(InventoryDTO inventoryDTO) {

        Product product = productService.getProductById(inventoryDTO.getProductId());
        Inventory inventory = inventoryRepository.findByProduct(product);
        inventory.setCreatedAt(utilsService.generateDateFormat());
        inventory.setCreatedBy(utilsService.getSuperUser());
        inventory.setProduct(product);
        inventory.setInventory(inventory.getInventory() + inventoryDTO.getStock());
        inventory.setUpdatedAt(utilsService.generateDateFormat());
        inventory.setUpdatedBy(utilsService.getSuperUser());

        inventoryRepository.saveAndFlush(inventory);

        InventoryLog inventoryLog = new InventoryLog();
        inventoryLog.setInventory(inventory);
        inventoryLog.setId(utilsService.generateId(Constants.INVENTORY_ORDER_PREFIX));
        inventoryLog.setInOut(InventoryLogEntry.IN);
        inventoryLog.setComments("Procured " + inventoryDTO.getStock() + " on " + utilsService.generateDateFormat());
        inventoryLog.setCreatedAt(utilsService.generateDateFormat());
        inventoryLogService.saveInventoryLog(inventoryLog);
    }

    public void deleteInventory(String id) {
        inventoryRepository.deleteById(id);
    }

    public List<InventoryCountDTO> getInventoryCount(){
        List<Inventory> inventories = inventoryRepository.findAll();
        List<InventoryCountDTO> countList = new ArrayList<InventoryCountDTO>();
        for (Inventory inventory: inventories) {
            InventoryCountDTO dto = new InventoryCountDTO();
            dto.setId(inventory.getId());
            dto.setProductId(inventory.getProduct().getId());
            dto.setName(inventory.getProduct().getName());
            dto.setCount(inventory.getInventory());
            countList.add(dto);
        }
        return countList;
    }

    public void incrementOrDecrementInventory(InventoryDTO inventoryDTO, String addOrDeduct, String comments){
        try{
            Product product = productService.getProductById(inventoryDTO.getProductId());
            Inventory inventory = inventoryRepository.findByProduct(product);
            inventory.setCreatedAt(utilsService.generateDateFormat());
            inventory.setCreatedBy(utilsService.getSuperUser());
            inventory.setProduct(product);
            if(addOrDeduct.equalsIgnoreCase(Constants.INVENTORY_INC)){
                inventory.setInventory(inventory.getInventory() + inventoryDTO.getStock());
            }
            else{
                inventory.setInventory(inventory.getInventory() - inventoryDTO.getStock());
            }
            inventory.setUpdatedAt(utilsService.generateDateFormat());
            inventory.setUpdatedBy(utilsService.getSuperUser());

            inventoryRepository.saveAndFlush(inventory);

            InventoryLog inventoryLog = new InventoryLog();
            inventoryLog.setInventory(inventory);
            inventoryLog.setId(utilsService.generateId(Constants.INVENTORY_ORDER_PREFIX));
            inventoryLog.setInOut(InventoryLogEntry.IN);
            if(addOrDeduct.equalsIgnoreCase(Constants.INVENTORY_INC)){
                inventoryLog.setComments("Procured " + inventoryDTO.getStock() + " on " + utilsService.generateDateFormat());
            }
            else{
                inventoryLog.setComments("Deducted " + inventoryDTO.getStock() + " on " + utilsService.generateDateFormat() + " for " + comments);
            }

            inventoryLog.setCreatedAt(utilsService.generateDateFormat());
            inventoryLogService.saveInventoryLog(inventoryLog);
        }
        catch (Exception e){

        }
    }
}
