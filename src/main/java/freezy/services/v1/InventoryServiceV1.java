package freezy.services.v1;


import freezy.dto.InventoryCountDTO;
import freezy.dto.v1.InventoryDTOV1;
import freezy.dto.v1.InventoryEntryV1;
import freezy.dto.v1.InventoryListV1;
import freezy.entities.v1.*;
import freezy.events.InwardDetailPublisher;
import freezy.repository.v1.InventoryRepositoryV1;
import freezy.utils.FreazyConstants;
import freezy.utils.FreazyUtilsService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
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
    FreazyUtilsService freazyUtilsService;

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

    @Autowired
    InwardDetailPublisher inwardDetailPublisher;


    public List<InventoryListV1> getAllInventory() {

        List<InventoryV1> inventoryV1s = inventoryRepositoryV1.findAllByOrderByCreatedAtDesc();
        List<InventoryListV1> inventories = new ArrayList<>();
        for(InventoryV1 v1: inventoryV1s){
            if(v1.getProduct() != null || v1.getAccessory() != null){
                InventoryListV1 listV1 = new InventoryListV1();
                CategoryUOMMapV1 categoryUOMMapV1 = null;
                listV1.setInventory(v1.getStock());
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
                inventory.setId(freazyUtilsService.generateId(FreazyConstants.INVENTORY_ORDER_PREFIX));
                inventory.setStock(inventoryDTO.getQuantity());
            }
            else{
                inventory.setStock(inventory.getStock() + inventoryDTO.getQuantity());
            }
            inventory.setCreatedAt(freazyUtilsService.generateDate());
            inventory.setCreatedBy(freazyUtilsService.getSuperUserV1());
            inventory.setProduct(product);
            inventory.setUpdatedAt(freazyUtilsService.generateDate());
            inventory.setUpdatedBy(freazyUtilsService.getSuperUserV1());
        }
        if(inventoryDTO.getType().equalsIgnoreCase(InventoryTypeV1.ACCESSORY.name())){
            AccessoryV1 accessoryV1 = accessoryServiceV1.getAccessoryById(inventoryDTO.getAccessoryId());
            inventory = inventoryRepositoryV1.findByAccessory(accessoryV1);
            if(null == inventory){
                inventory = new InventoryV1();
                inventory.setId(freazyUtilsService.generateId(FreazyConstants.INVENTORY_ORDER_PREFIX));
                inventory.setStock(inventoryDTO.getQuantity());
            }
            else{
                inventory.setStock(inventory.getStock() + inventoryDTO.getQuantity());
            }
            inventory.setCreatedAt(freazyUtilsService.generateDate());
            inventory.setCreatedBy(freazyUtilsService.getSuperUserV1());
            inventory.setAccessory(accessoryV1);
            inventory.setUpdatedAt(freazyUtilsService.generateDate());
            inventory.setUpdatedBy(freazyUtilsService.getSuperUserV1());
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
            dto.setCount(inventory.getStock());
            countList.add(dto);
        }
        return countList;
    }

    @Transactional
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
            if(null != inventoryEntryV1.getServices())for(InventoryDTOV1 inventoryDTO : inventoryEntryV1.getServices()){
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
            consignmentV1.setCreatedAt(freazyUtilsService.generateDate());
            consignmentV1.setId(freazyUtilsService.generateId(FreazyConstants.CONSIGNMENT_PREFIX));
            if(null != inOrOut && inOrOut.equalsIgnoreCase(FreazyConstants.INVENTORY_INC)){
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
            List<InventoryLogV1> logs = inventoryLogServiceV1.getAllLogsByIduSerial(dto.getIduSerialNo());
            if(null == logs || logs.size() ==0) return false;
        }
        return true;
    }

    public Boolean validateIDU(InventoryEntryV1 inventoryEntryV1) {
        for(InventoryDTOV1 dto: inventoryEntryV1.getProducts()){
            List<InventoryLogV1> logs = inventoryLogServiceV1.getAllLogsByIduSerial(dto.getIduSerialNo());
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

    public Boolean validateProductsOrAccessoriesOrServices(InventoryEntryV1 inventoryEntryV1) {
        if(null != inventoryEntryV1.getServices()){
            if(inventoryEntryV1.getServices().size() == 0 && inventoryEntryV1.getAccessories().size() == 0
                    && inventoryEntryV1.getProducts().size() == 0 ) return false;
        }
        if(null == inventoryEntryV1.getServices()){
            if(inventoryEntryV1.getAccessories().size() == 0
                    && inventoryEntryV1.getProducts().size() == 0 ) return false;
        }
        return true;
    }

    public Integer createProductEntry(InventoryDTOV1 inventoryDTO, InventoryEntryV1 inventoryEntryV1, String inOrOut, ConsignmentV1 consignmentV1){

        Integer totalAmount = 0;
        ProductV1 product = productServiceV1.getProductV1ById(inventoryDTO.getProductId());
        InventoryV1 inventory = inventoryRepositoryV1.findByProduct(product);
        if(null == inventory){
            inventory = new InventoryV1();
            inventory.setId(freazyUtilsService.generateId(FreazyConstants.INVENTORY_ORDER_PREFIX));
        }
        inventory.setType(InventoryTypeV1.PRODUCT);
        inventory.setCreatedAt(freazyUtilsService.generateDate());
        inventory.setCreatedBy(userServiceV1.getUserById(inventoryEntryV1.getUserId()));
        inventory.setProduct(product);
        if(inOrOut.equalsIgnoreCase(FreazyConstants.INVENTORY_INC)){
            inventory.setStock(((null != inventory.getStock())?inventory.getStock():0) + inventoryDTO.getQuantity());
        }
        else{
            inventory.setStock(((null != inventory.getStock())?inventory.getStock():0) - inventoryDTO.getQuantity());
        }
        inventory.setUpdatedAt(freazyUtilsService.generateDate());
        inventory.setUpdatedBy(freazyUtilsService.getSuperUserV1());
        totalAmount = totalAmount + (inventoryDTO.getUnitPrice() * inventoryDTO.getQuantity());

        inventoryRepositoryV1.saveAndFlush(inventory);

        InventoryLogV1 inventoryLog = new InventoryLogV1();
        inventoryLog.setInventory(inventory);
        inventoryLog.setId(freazyUtilsService.generateId(FreazyConstants.INVENTORY_ORDER_PREFIX));
        inventoryLog.setUnitPrice(freazyUtilsService.returnDefaultInt(inventoryDTO.getUnitPrice()));
        inventoryLog.setDiscountAmount(freazyUtilsService.returnDefaultInt(inventoryDTO.getDiscountAmount()));
        inventoryLog.setEffectivePrice(freazyUtilsService.returnDefaultInt(inventoryDTO.getEffectivePrice()));
        inventoryLog.setSubTotal(freazyUtilsService.returnDefaultDouble(inventoryDTO.getSubTotal()));
        inventoryLog.setTaxPercent(freazyUtilsService.returnDefaultDouble(inventoryDTO.getGstValue().getGstValue()));
        inventoryLog.setTaxLabel(freazyUtilsService.returnDefaultString(inventoryDTO.getGstValue().getGstRate()));
        inventoryLog.setQuantity(inventoryDTO.getQuantity());
        inventoryLog.setUpdatedStock(inventory.getStock());
        inventoryLog.setType(InventoryTypeV1.PRODUCT);
        inventoryLog.setIduSerial(freazyUtilsService.returnDefaultString(inventoryDTO.getIduSerialNo()));
        inventoryLog.setOduSerial(freazyUtilsService.returnDefaultString(inventoryDTO.getOduSerialNo()));
        if(inOrOut.equalsIgnoreCase(FreazyConstants.INVENTORY_INC)){
            inventoryLog.setInOut(InventoryLogEntryV1.IN);
            inventoryLog.setComments("Notes: " + inventoryEntryV1.getComments());
            publishInwardDetailEvent(inventoryDTO.getQuantity(), product.getName());
        }
        else{
            inventoryLog.setInOut(InventoryLogEntryV1.OUT);
            inventoryLog.setComments("Notes: " + inventoryEntryV1.getComments());
        }

        inventoryLog.setCreatedAt(freazyUtilsService.generateDate());
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
            inventory.setId(freazyUtilsService.generateId(FreazyConstants.INVENTORY_ORDER_PREFIX));
        }
        inventory.setType(InventoryTypeV1.ACCESSORY);
        inventory.setCreatedAt(freazyUtilsService.generateDate());
        inventory.setCreatedBy(userServiceV1.getUserById(inventoryEntryV1.getUserId()));
        inventory.setAccessory(accessoryV1);
        if(inOrOut.equalsIgnoreCase(FreazyConstants.INVENTORY_INC)){
            inventory.setStock(((null != inventory.getStock())?inventory.getStock():0) + inventoryDTO.getQuantity());
        }
        else{
            inventory.setStock(((null != inventory.getStock())?inventory.getStock():0) - inventoryDTO.getQuantity());
        }
        inventory.setUpdatedAt(freazyUtilsService.generateDate());
        inventory.setUpdatedBy(freazyUtilsService.getSuperUserV1());
        totalAmount = totalAmount + (inventoryDTO.getUnitPrice() * inventoryDTO.getQuantity());

        inventoryRepositoryV1.saveAndFlush(inventory);

        InventoryLogV1 inventoryLog = new InventoryLogV1();
        UserV1 user = userServiceV1.getUserById(inventoryEntryV1.getUserId());
        inventoryLog.setInventory(inventory);
        inventoryLog.setId(freazyUtilsService.generateId(FreazyConstants.INVENTORY_ORDER_PREFIX));
        inventoryLog.setUnitPrice(freazyUtilsService.returnDefaultInt(inventoryDTO.getUnitPrice()));
        inventoryLog.setDiscountAmount(freazyUtilsService.returnDefaultInt(inventoryDTO.getDiscountAmount()));
        inventoryLog.setEffectivePrice(freazyUtilsService.returnDefaultInt(inventoryDTO.getEffectivePrice()));
        inventoryLog.setSubTotal(freazyUtilsService.returnDefaultDouble(inventoryDTO.getSubTotal()));
        inventoryLog.setTaxPercent(freazyUtilsService.returnDefaultDouble(inventoryDTO.getGstValue().getGstValue()));
        inventoryLog.setTaxLabel(freazyUtilsService.returnDefaultString(inventoryDTO.getGstValue().getGstRate()));
        inventoryLog.setQuantity(inventoryDTO.getQuantity());
        inventoryLog.setUpdatedStock(inventory.getStock());
        inventoryLog.setType(InventoryTypeV1.ACCESSORY);
        inventoryLog.setIduSerial(freazyUtilsService.returnDefaultString(inventoryDTO.getIduSerialNo()));
        inventoryLog.setOduSerial(freazyUtilsService.returnDefaultString(inventoryDTO.getOduSerialNo()));
        if(inOrOut.equalsIgnoreCase(FreazyConstants.INVENTORY_INC)){
            inventoryLog.setInOut(InventoryLogEntryV1.IN);
            inventoryLog.setComments("Notes: " + inventoryEntryV1.getComments());
        }
        else{
            inventoryLog.setInOut(InventoryLogEntryV1.OUT);
            inventoryLog.setComments("Notes: " + inventoryEntryV1.getComments());
        }

        inventoryLog.setCreatedAt(freazyUtilsService.generateDate());
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
            inventory.setId(freazyUtilsService.generateId(FreazyConstants.INVENTORY_ORDER_PREFIX));
        }
        inventory.setType(InventoryTypeV1.SERVICE);
        inventory.setCreatedAt(freazyUtilsService.generateDate());
        inventory.setCreatedBy(userServiceV1.getUserById(inventoryEntryV1.getUserId()));
        inventory.setService(serviceV1);
        inventory.setStock(0);
        inventory.setUpdatedAt(freazyUtilsService.generateDate());
        inventory.setUpdatedBy(freazyUtilsService.getSuperUserV1());
        totalAmount = totalAmount + (inventoryDTO.getUnitPrice() * inventoryDTO.getQuantity());

        inventoryRepositoryV1.saveAndFlush(inventory);

        InventoryLogV1 inventoryLog = new InventoryLogV1();
        UserV1 user = userServiceV1.getUserById(inventoryEntryV1.getUserId());
        inventoryLog.setInventory(inventory);
        inventoryLog.setId(freazyUtilsService.generateId(FreazyConstants.INVENTORY_ORDER_PREFIX));
        inventoryLog.setUnitPrice(freazyUtilsService.returnDefaultInt(inventoryDTO.getUnitPrice()));
        inventoryLog.setDiscountAmount(freazyUtilsService.returnDefaultInt(inventoryDTO.getDiscountAmount()));
        inventoryLog.setEffectivePrice(freazyUtilsService.returnDefaultInt(inventoryDTO.getEffectivePrice()));
        inventoryLog.setSubTotal(freazyUtilsService.returnDefaultDouble(inventoryDTO.getSubTotal()));
        inventoryLog.setTaxPercent(freazyUtilsService.returnDefaultDouble(inventoryDTO.getGstValue().getGstValue()));
        inventoryLog.setTaxLabel(freazyUtilsService.returnDefaultString(inventoryDTO.getGstValue().getGstRate()));
        inventoryLog.setQuantity(inventoryDTO.getQuantity());
        inventoryLog.setUpdatedStock(inventory.getStock());
        inventoryLog.setType(InventoryTypeV1.SERVICE);
        inventoryLog.setIduSerial(freazyUtilsService.returnDefaultString(inventoryDTO.getIduSerialNo()));
        inventoryLog.setOduSerial(freazyUtilsService.returnDefaultString(inventoryDTO.getOduSerialNo()));
        if(inOrOut.equalsIgnoreCase(FreazyConstants.INVENTORY_DEDUCT)){
            inventoryLog.setInOut(InventoryLogEntryV1.OUT);
            inventoryLog.setOduSerial("NA");
            inventoryLog.setComments("Notes: " + inventoryEntryV1.getComments());
        }
        if(inOrOut.equalsIgnoreCase(FreazyConstants.INVENTORY_INC)){
            inventoryLog.setInOut(InventoryLogEntryV1.IN);
            inventoryLog.setOduSerial("NA");
            inventoryLog.setComments("Notes: " + inventoryEntryV1.getComments());
        }

        inventoryLog.setCreatedAt(freazyUtilsService.generateDate());
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
                Integer currentStock = inventory.getStock();
                currentStock = currentStock + deltaMap.get(inventoryId);
                inventory.setStock(currentStock);
                updatedInventories.add(inventory);
            }
            if(updatedInventories.size() > 0)inventoryRepositoryV1.saveAllAndFlush(updatedInventories);
        }
        catch (Exception e){

        }
    }

    public void publishInwardDetailEvent(Integer quantity, String productName){
        String userName = freazyUtilsService.getSuperUserV1().getId();
        inwardDetailPublisher.publishEvent(userName, quantity, productName);
    }

}
