package freezy.services.v1;


import freezy.dto.InventoryCountDTO;
import freezy.dto.InventoryDTO;
import freezy.dto.v1.InventoryDTOV1;
import freezy.dto.v1.InventoryEntryV1;
import freezy.dto.v1.InventoryListV1;
import freezy.entities.Inventory;
import freezy.entities.Product;
import freezy.entities.v1.*;
import freezy.repository.v1.InventoryRepositoryV1;
import freezy.utils.Constants;
import freezy.utils.UtilsService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Autowired
    ConsignmentServiceV1 consignmentServiceV1;

    @Autowired
    AccessoryServiceV1 accessoryServiceV1;

    @Autowired
    ServicesServiceV1 servicesServiceV1;


    public List<InventoryListV1> getAllInventory() {

        List<InventoryV1> inventoryV1s = inventoryRepositoryV1.findAllByOrderByCreatedAtDesc();
        List<InventoryListV1> inventories = new ArrayList<>();
        for(InventoryV1 v1: inventoryV1s){
            if(v1.getProduct() != null || v1.getAccessory() != null){
                InventoryListV1 listV1 = new InventoryListV1();
                CategoryUOMMapV1 categoryUOMMapV1 = null;
                listV1.setInventory(v1.getInventory());
                listV1.setId(v1.getId());
                if(null != v1.getProduct()){
                    listV1.setProduct(v1.getProduct());
                    listV1.setType(InventoryTypeV1.PRODUCT.name());
                    categoryUOMMapV1 = categoryUOMMapServiceV1.getUOMByCategory(v1.getProduct().getCategory().getId());
                }
                if(null != v1.getAccessory()){
                    listV1.setAccessory(v1.getAccessory());
                    listV1.setType(InventoryTypeV1.ACCESSORY.name());
                    categoryUOMMapV1 = categoryUOMMapServiceV1.getUOMByCategory(v1.getAccessory().getCategory().getId());
                }
                if(null != categoryUOMMapV1 && !categoryUOMMapV1.getMultiple().equalsIgnoreCase("1")){
                    listV1.setUom(categoryUOMMapV1.getMultiple() + " " + categoryUOMMapV1.getUomv1().name());
                }
                else{
                    if(null != categoryUOMMapV1)listV1.setUom(categoryUOMMapV1.getUomv1().name());
                }
                inventories.add(listV1);
            }
        }
        return inventories;
    }

    public InventoryV1 getInventoryById(String id) {
        return inventoryRepositoryV1.findById(id).orElse(null);
    }

    public void saveInventory(InventoryDTOV1 inventoryDTO) {

        InventoryV1 inventory = null;
        if(inventoryDTO.getType().equalsIgnoreCase(InventoryTypeV1.PRODUCT.name())){
            ProductV1 product = productServiceV1.getProductV1ById(inventoryDTO.getProductId());
            inventory = inventoryRepositoryV1.findByProduct(product);
            if(null == inventory){
                inventory = new InventoryV1();
                inventory.setId(utilsService.generateId(Constants.INVENTORY_ORDER_PREFIX));
                inventory.setInventory(inventoryDTO.getQuantity());
            }
            else{
                inventory.setInventory(inventory.getInventory() + inventoryDTO.getQuantity());
            }
            inventory.setCreatedAt(utilsService.generateDateFormat());
            inventory.setCreatedBy(utilsService.getSuperUserV1());
            inventory.setProduct(product);
            inventory.setUpdatedAt(utilsService.generateDateFormat());
            inventory.setUpdatedBy(utilsService.getSuperUserV1());
        }
        if(inventoryDTO.getType().equalsIgnoreCase(InventoryTypeV1.ACCESSORY.name())){
            AccessoryV1 accessoryV1 = accessoryServiceV1.getAccessoryById(inventoryDTO.getAccessoryId());
            inventory = inventoryRepositoryV1.findByAccessory(accessoryV1);
            if(null == inventory){
                inventory = new InventoryV1();
                inventory.setId(utilsService.generateId(Constants.INVENTORY_ORDER_PREFIX));
                inventory.setInventory(inventoryDTO.getQuantity());
            }
            else{
                inventory.setInventory(inventory.getInventory() + inventoryDTO.getQuantity());
            }
            inventory.setCreatedAt(utilsService.generateDateFormat());
            inventory.setCreatedBy(utilsService.getSuperUserV1());
            inventory.setAccessory(accessoryV1);
            inventory.setUpdatedAt(utilsService.generateDateFormat());
            inventory.setUpdatedBy(utilsService.getSuperUserV1());
        }

        inventoryRepositoryV1.saveAndFlush(inventory);

//        InventoryLogV1 inventoryLog = new InventoryLogV1();
//        inventoryLog.setInventory(inventory);
//        inventoryLog.setId(utilsService.generateId(Constants.INVENTORY_ORDER_PREFIX));
//        inventoryLog.setInOut(InventoryLogEntryV1.IN);
//        inventoryLog.setComments("Procured " + inventoryDTO.getStock() + " on " + utilsService.generateDateFormat());
//        inventoryLog.setCreatedAt(utilsService.generateDateFormat());
//        inventoryLogServiceV1.saveInventoryLog(inventoryLog);
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

    public ConsignmentV1 incrementOrDecrementInventory(InventoryEntryV1 inventoryEntryV1, String inOrOut, ConsignmentV1 existingConsignment){
//        try{
        ConsignmentV1 consignmentV1 = existingConsignment;
        if(null != inventoryEntryV1 && (inventoryEntryV1.getProducts().size() > 0 || inventoryEntryV1.getAccessories().size() > 0
                || inventoryEntryV1.getServices().size() > 0)){
            if(null == consignmentV1)consignmentV1 = createConsignment(inventoryEntryV1, inOrOut);
            Integer totalAmount = 0;

            for(InventoryDTOV1 inventoryDTO : inventoryEntryV1.getProducts()){
                if(null != inventoryDTO){
                    totalAmount = totalAmount + createProductEntry(inventoryDTO, inventoryEntryV1, inOrOut, consignmentV1);
                }
            }
            for(InventoryDTOV1 inventoryDTO : inventoryEntryV1.getAccessories()){
                if(null != inventoryDTO)
                {
                    totalAmount = totalAmount + createAccessoryEntry(inventoryDTO, inventoryEntryV1, inOrOut, consignmentV1);
                }
            }
            for(InventoryDTOV1 inventoryDTO : inventoryEntryV1.getServices()){
                if(null != inventoryDTO){
                    totalAmount = totalAmount + createServiceEntry(inventoryDTO, inventoryEntryV1, inOrOut, consignmentV1);
                }
            }
            consignmentV1.setTotalAmount(totalAmount);
            consignmentServiceV1.saveConsignment(consignmentV1);

        }

//        }
//        catch (Exception e){
//
//        }
        return consignmentV1;
    }

    private ConsignmentV1 createConsignment(InventoryEntryV1 inventoryEntryV1, String inOrOut) {
        ConsignmentV1 consignmentV1;
        Integer itemCount = 0;
        try{
            consignmentV1 = new ConsignmentV1();
            InventoryLogEntryV1 direction = null;
            consignmentV1.setComments(inventoryEntryV1.getComments());
            consignmentV1.setCreatedAt(utilsService.generateDateFormat());
            consignmentV1.setId(utilsService.generateId(Constants.CONSIGNMENT_PREFIX));
            if(null != inOrOut && inOrOut.equalsIgnoreCase(Constants.INVENTORY_INC)){
                direction = InventoryLogEntryV1.IN;
            }
            else{
                direction = InventoryLogEntryV1.OUT;
            }
            consignmentV1.setInOut(direction);
            if(null != inventoryEntryV1.getProducts())itemCount = itemCount + inventoryEntryV1.getProducts().size();
            if(null != inventoryEntryV1.getAccessories())itemCount = itemCount + inventoryEntryV1.getAccessories().size();
            if(null != inventoryEntryV1.getServices())itemCount = itemCount + inventoryEntryV1.getServices().size();
            consignmentV1.setItemCount(itemCount);
            consignmentV1.setCreatedFor(userServiceV1.getUserById(inventoryEntryV1.getUserId()));
            consignmentV1.setTotalAmount(0);
            consignmentServiceV1.saveConsignment(consignmentV1);
            return consignmentV1;
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public List<InventoryLogV1> getLogsByConsignment(String consignmentId) {
        ConsignmentV1 consignmentV1 = consignmentServiceV1.getConsignmentById(consignmentId);
        return inventoryLogServiceV1.getAllLogsByConsignment(consignmentV1);
    }

    public Boolean validateODU(InventoryEntryV1 inventoryEntryV1) {
        for(InventoryDTOV1 dto: inventoryEntryV1.getProducts()){
            List<InventoryLogV1> logs = inventoryLogServiceV1.getAllLogsByIduSerial(dto.getSerialNo());
            if(null == logs || logs.size() ==0) return false;
        }
        return true;
    }

    public Boolean validateIDU(InventoryEntryV1 inventoryEntryV1) {
        for(InventoryDTOV1 dto: inventoryEntryV1.getProducts()){
            List<InventoryLogV1> logs = inventoryLogServiceV1.getAllLogsByIduSerial(dto.getSerialNo());
            if(null != logs && logs.size() > 0) return false;
        }
        return true;
    }

    public Boolean validateQuantity(InventoryEntryV1 inventoryEntryV1) {
        for(InventoryDTOV1 dto: inventoryEntryV1.getProducts()){
            if(null != dto && dto.getQuantity() > 1) return false;
        }
        return true;
    }

    public Integer createProductEntry(InventoryDTOV1 inventoryDTO, InventoryEntryV1 inventoryEntryV1, String inOrOut, ConsignmentV1 consignmentV1){

        Integer totalAmount = 0;
        ProductV1 product = productServiceV1.getProductV1ById(inventoryDTO.getProductId());
        InventoryV1 inventory = inventoryRepositoryV1.findByProduct(product);
        if(null == inventory){
            inventory = new InventoryV1();
            inventory.setId(utilsService.generateId(Constants.INVENTORY_ORDER_PREFIX));
        }
        inventory.setType(InventoryTypeV1.PRODUCT);
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
        totalAmount = totalAmount + (inventoryDTO.getUnitPrice() * inventoryDTO.getQuantity());

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
        inventoryLog.setType(InventoryTypeV1.PRODUCT);
        if(inOrOut.equalsIgnoreCase(Constants.INVENTORY_INC)){
            inventoryLog.setInOut(InventoryLogEntryV1.IN);
            inventoryLog.setIduSerial(inventoryDTO.getSerialNo());
            inventoryLog.setComments("Procured " + inventoryDTO.getQuantity() + " " + product.getName() + " (IDU: " + inventoryDTO.getSerialNo() + ") on " + utilsService.generateDateFormat() + " from " +
                    userFirstName + ", Notes: " + inventoryEntryV1.getComments());
        }
        else{
            inventoryLog.setInOut(InventoryLogEntryV1.OUT);
            inventoryLog.setOduSerial(inventoryDTO.getSerialNo());
            inventoryLog.setComments("Delivered " + inventoryDTO.getQuantity() + " " + product.getName() + "(ODU: " + inventoryDTO.getSerialNo() + ") on " + utilsService.generateDateFormat() + " for " +
                    userFirstName + ", Notes: " + inventoryEntryV1.getComments());
        }

        inventoryLog.setCreatedAt(utilsService.generateDateFormat());
        inventoryLog.setConsignment(consignmentV1);
        inventoryLogServiceV1.saveInventoryLog(inventoryLog);
        return totalAmount;

    }
    public Integer createAccessoryEntry(InventoryDTOV1 inventoryDTO, InventoryEntryV1 inventoryEntryV1, String inOrOut, ConsignmentV1 consignmentV1){
        Integer totalAmount = 0;
        AccessoryV1 accessoryV1 = accessoryServiceV1.getAccessoryById(inventoryDTO.getAccessoryId());
        InventoryV1 inventory = inventoryRepositoryV1.findByAccessory(accessoryV1);
        if(null == inventory){
            inventory = new InventoryV1();
            inventory.setId(utilsService.generateId(Constants.INVENTORY_ORDER_PREFIX));
        }
        inventory.setType(InventoryTypeV1.ACCESSORY);
        inventory.setCreatedAt(utilsService.generateDateFormat());
        inventory.setCreatedBy(userServiceV1.getUserById(inventoryEntryV1.getUserId()));
        inventory.setAccessory(accessoryV1);
        if(inOrOut.equalsIgnoreCase(Constants.INVENTORY_INC)){
            inventory.setInventory(((null != inventory.getInventory())?inventory.getInventory():0) + inventoryDTO.getQuantity());
        }
        else{
            inventory.setInventory(((null != inventory.getInventory())?inventory.getInventory():0) - inventoryDTO.getQuantity());
        }
        inventory.setUpdatedAt(utilsService.generateDateFormat());
        inventory.setUpdatedBy(utilsService.getSuperUserV1());
        totalAmount = totalAmount + (inventoryDTO.getUnitPrice() * inventoryDTO.getQuantity());

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
        inventoryLog.setType(InventoryTypeV1.ACCESSORY);
        if(inOrOut.equalsIgnoreCase(Constants.INVENTORY_INC)){
            inventoryLog.setInOut(InventoryLogEntryV1.IN);
            inventoryLog.setIduSerial(inventoryDTO.getSerialNo());
            inventoryLog.setComments("Procured " + inventoryDTO.getQuantity() + " " +  accessoryV1.getName() + " on " + utilsService.generateDateFormat() + " from " +
                    userFirstName + ", Notes: " + inventoryEntryV1.getComments());
        }
        else{
            inventoryLog.setInOut(InventoryLogEntryV1.OUT);
            inventoryLog.setOduSerial(inventoryDTO.getSerialNo());
            inventoryLog.setComments("Delivered " + inventoryDTO.getQuantity() + " " +  accessoryV1.getName() + " on " + utilsService.generateDateFormat() + " for " +
                    userFirstName + ", Notes: " + inventoryEntryV1.getComments());
        }

        inventoryLog.setCreatedAt(utilsService.generateDateFormat());
        inventoryLog.setConsignment(consignmentV1);
        inventoryLogServiceV1.saveInventoryLog(inventoryLog);
        return totalAmount;
    }

    public Integer createServiceEntry(InventoryDTOV1 inventoryDTO, InventoryEntryV1 inventoryEntryV1, String inOrOut, ConsignmentV1 consignmentV1){
        Integer totalAmount = 0;
        ServiceV1 serviceV1 = servicesServiceV1.getServiceById(inventoryDTO.getServiceId());
        InventoryV1 inventory = inventoryRepositoryV1.findByService(serviceV1);
        if(null == inventory){
            inventory = new InventoryV1();
            inventory.setId(utilsService.generateId(Constants.INVENTORY_ORDER_PREFIX));
        }
        inventory.setType(InventoryTypeV1.SERVICE);
        inventory.setCreatedAt(utilsService.generateDateFormat());
        inventory.setCreatedBy(userServiceV1.getUserById(inventoryEntryV1.getUserId()));
        inventory.setService(serviceV1);
        inventory.setInventory(0);
        inventory.setUpdatedAt(utilsService.generateDateFormat());
        inventory.setUpdatedBy(utilsService.getSuperUserV1());
        totalAmount = totalAmount + (inventoryDTO.getUnitPrice() * inventoryDTO.getQuantity());

        inventoryRepositoryV1.saveAndFlush(inventory);

        InventoryLogV1 inventoryLog = new InventoryLogV1();
        UserV1 user = userServiceV1.getUserById(inventoryEntryV1.getUserId());
        String userFirstName = (null != user.getFirst_name())?user.getFirst_name():" ";
        String userLastName = " ";
        inventoryLog.setInventory(inventory);
        inventoryLog.setId(utilsService.generateId(Constants.INVENTORY_ORDER_PREFIX));
        inventoryLog.setAmount(inventoryDTO.getUnitPrice());
        inventoryLog.setQuantity(inventoryDTO.getQuantity());
        inventoryLog.setUpdatedStock(0);
        inventoryLog.setType(InventoryTypeV1.SERVICE);
        if(inOrOut.equalsIgnoreCase(Constants.INVENTORY_DEDUCT)){
            inventoryLog.setInOut(InventoryLogEntryV1.OUT);
            inventoryLog.setOduSerial("NA");
            inventoryLog.setComments("Service of " + serviceV1.getName() + " offered on " + utilsService.generateDateFormat() + " for " +
                    userFirstName + ", Notes: " + inventoryEntryV1.getComments());
        }
        if(inOrOut.equalsIgnoreCase(Constants.INVENTORY_INC)){
            inventoryLog.setInOut(InventoryLogEntryV1.IN);
            inventoryLog.setOduSerial("NA");
            inventoryLog.setComments("Service of " + serviceV1.getName() + " added on " + utilsService.generateDateFormat() + " for " +
                    userFirstName + ", Notes: " + inventoryEntryV1.getComments());
        }

        inventoryLog.setCreatedAt(utilsService.generateDateFormat());
        inventoryLog.setConsignment(consignmentV1);
        inventoryLogServiceV1.saveInventoryLog(inventoryLog);
        return totalAmount;
    }

    public void undoConsignment(ConsignmentV1 consignmentV1){
        try{
            List<InventoryLogV1> logs = inventoryLogServiceV1.getAllLogsByConsignment(consignmentV1);
            Map<String, Integer> deltaMap = new HashMap<>();
            for(InventoryLogV1 log: logs){
                if(log.getInOut().equals(InventoryLogEntryV1.IN)){
                    deltaMap.put(log.getInventory().getId(), (-1) * log.getQuantity());
                }
                else{
                    deltaMap.put(log.getInventory().getId(), log.getQuantity());
                }
                inventoryLogServiceV1.deleteInventoryLog(log.getId());
            }
            List<InventoryV1> updatedInventories = new ArrayList<>();
            for(String inventoryId: deltaMap.keySet()){
                InventoryV1 inventory = getInventoryById(inventoryId);
                Integer currentStock = inventory.getInventory();
                currentStock = currentStock + deltaMap.get(inventoryId);
                inventory.setInventory(currentStock);
                updatedInventories.add(inventory);
            }
            if(updatedInventories.size() > 0)inventoryRepositoryV1.saveAllAndFlush(updatedInventories);
        }
        catch (Exception e){

        }

    }

}
