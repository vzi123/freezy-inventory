package freezy.services;


import freezy.dto.InventoryCountDTO;
import freezy.dto.InventoryDTO;
import freezy.entities.Inventory;
import freezy.entities.InventoryLog;
import freezy.entities.InventoryLogEntry;
import freezy.entities.Product;
import freezy.repository.InventoryRepository;
import freezy.utils.FreazyConstants;
import freezy.utils.FreazyUtilsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class InventoryService {
    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    InventoryLogService inventoryLogService;

    @Autowired
    FreazyUtilsService freazyUtilsService;

    @Autowired
    ProductService productService;

    public List<Inventory> getAllInventory() {
        return inventoryRepository.findAllByOrderByIdAsc();
    }

    public Inventory getInventoryById(String id) {
        return inventoryRepository.findById(id).orElse(null);
    }

    public void saveInventory(InventoryDTO inventoryDTO) {

        Product product = productService.getProductById(inventoryDTO.getProductId());
        Inventory inventory = inventoryRepository.findByProduct(product);
        if(null == inventory){
            inventory = new Inventory();
            inventory.setId(freazyUtilsService.generateId(FreazyConstants.INVENTORY_ORDER_PREFIX));
            inventory.setInventory(inventoryDTO.getStock());
        }
        else{
            inventory.setInventory(inventory.getInventory() + inventoryDTO.getStock());
        }
        inventory.setCreatedAt(freazyUtilsService.generateDateFormat());
        inventory.setCreatedBy(freazyUtilsService.getSuperUser());
        inventory.setProduct(product);
        inventory.setUpdatedAt(freazyUtilsService.generateDateFormat());
        inventory.setUpdatedBy(freazyUtilsService.getSuperUser());

        inventoryRepository.saveAndFlush(inventory);

        InventoryLog inventoryLog = new InventoryLog();
        inventoryLog.setInventory(inventory);
        inventoryLog.setId(freazyUtilsService.generateId(FreazyConstants.INVENTORY_ORDER_PREFIX));
        inventoryLog.setInOut(InventoryLogEntry.IN);
        inventoryLog.setComments("Procured " + inventoryDTO.getStock() + " on " + freazyUtilsService.generateDateFormat());
        inventoryLog.setCreatedAt(freazyUtilsService.generateDateFormat());
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
            inventory.setCreatedAt(freazyUtilsService.generateDateFormat());
            inventory.setCreatedBy(freazyUtilsService.getSuperUser());
            inventory.setProduct(product);
            if(addOrDeduct.equalsIgnoreCase(FreazyConstants.INVENTORY_INC)){
                inventory.setInventory(inventory.getInventory() + inventoryDTO.getStock());
            }
            else{
                inventory.setInventory(inventory.getInventory() - inventoryDTO.getStock());
            }
            inventory.setUpdatedAt(freazyUtilsService.generateDateFormat());
            inventory.setUpdatedBy(freazyUtilsService.getSuperUser());

            inventoryRepository.saveAndFlush(inventory);

            InventoryLog inventoryLog = new InventoryLog();
            inventoryLog.setInventory(inventory);
            inventoryLog.setId(freazyUtilsService.generateId(FreazyConstants.INVENTORY_ORDER_PREFIX));
            if(addOrDeduct.equalsIgnoreCase(FreazyConstants.INVENTORY_INC)){
                inventoryLog.setInOut(InventoryLogEntry.IN);
                inventoryLog.setComments("Procured " + inventoryDTO.getStock() + " on " + freazyUtilsService.generateDateFormat());
            }
            else{
                inventoryLog.setInOut(InventoryLogEntry.OUT);
                inventoryLog.setComments("Deducted " + inventoryDTO.getStock() + " on " + freazyUtilsService.generateDateFormat() + " for " + comments);
            }

            inventoryLog.setCreatedAt(freazyUtilsService.generateDateFormat());
            inventoryLogService.saveInventoryLog(inventoryLog);
        }
        catch (Exception e){

        }
    }
}
