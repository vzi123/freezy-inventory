package freezy.services.v1;



import freezy.dto.v1.ConsignmentDetailsDTO;
import freezy.dto.v1.GoodsDetailDTO;
import freezy.entities.*;
import freezy.repository.v1.ConsignmentRepositoryV1;
import freezy.utils.Constants;
import freezy.utils.PdfGenerateService;
import freezy.utils.UtilsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@org.springframework.stereotype.Service
public class ConsignmentServiceV1 {

    @Autowired
    ConsignmentRepositoryV1 consignmentRepositoryV1;

    @Autowired
    UtilsService utilsService;

    @Autowired
    PdfGenerateService pdfGenerateService;

    @Autowired
    UserServiceV1 userServiceV1;

    @Autowired
    ProductServiceV1 productServiceV1;

    @Autowired
    AccessoryServiceV1 accessoryServiceV1;

    @Autowired
    ServicesServiceV1 servicesServiceV1;

    @Autowired
    ConsignmentDetailServiceV1 consignmentDetailServiceV1;

    @Autowired
    InventoryServiceV1 inventoryServiceV1;

    @Autowired
    InventoryLogServiceV1 inventoryLogServiceV1;

    public List<Consignment> getAllConsignments() {
        return consignmentRepositoryV1.findAllByOrderByCreatedAtDesc();
    }

    public Consignment getConsignmentById(String id) {
        return consignmentRepositoryV1.findById(id).orElse(null);
    }

    public List<ConsignmentDetails> getConsignmentDetails(String consignmentId){
        return consignmentDetailServiceV1.getDetailsByConsignment(consignmentRepositoryV1.findById(consignmentId).orElse(null));
    }

    public void saveConsignment(Consignment consignment) {
        consignmentRepositoryV1.saveAndFlush(consignment);
    }

    public void deleteConsignment(String id) {
        consignmentRepositoryV1.deleteById(id);
    }

    public ResponseEntity<byte[]> generateDC(String consignmentId) throws Exception{
        byte[] dcFile = pdfGenerateService.generateDeliveryChallan(getConsignmentById(consignmentId));
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("inline", "document.pdf");
        return new ResponseEntity<>(dcFile, headers, HttpStatus.OK);
    }

    @Transactional
    public Consignment createConsignmentDetails(ConsignmentDetailsDTO dto, String incOrDec) {
        Consignment consignment = null;
        try{
            consignment = createConsignment(dto, incOrDec);
            createProductEntries(consignment, dto);
            createAccessoryEntries(consignment, dto);
            createServiceEntries(consignment, dto);
            adjustInventory(consignment, dto);
            createInventoryLog(consignment, dto);
        }
        catch (Exception e){
            System.out.println("Error: " + e.getMessage());
        }
        return consignment;
    }

    private Consignment createConsignment(ConsignmentDetailsDTO dto, String inOrOut) {
        Consignment consignment;
        try{
            consignment = new Consignment();
            InventoryLogEntryV1 direction = null;
            consignment.setComments(dto.getComments());
            consignment.setCreatedAt(utilsService.generateDateFormat());
            consignment.setId(utilsService.generateId(Constants.CONSIGNMENT_PREFIX));
            if(null != inOrOut && inOrOut.equalsIgnoreCase(InventoryLogEntryV1.IN.name())){
                direction = InventoryLogEntryV1.IN;
            }
            else if(null != inOrOut && inOrOut.equalsIgnoreCase(InventoryLogEntryV1.RETURN.name())){
                direction = InventoryLogEntryV1.RETURN;
            }
            else{
                direction = InventoryLogEntryV1.OUT;
            }
            consignment.setInOut(direction);
            consignment.setCreatedFor(userServiceV1.getUserById(dto.getUserId()));
            consignment.setTotalAmount(0);
            saveConsignment(consignment);
            return consignment;
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Consignment createProductEntries(Consignment consignment, ConsignmentDetailsDTO details){
        try{
            List<GoodsDetailDTO> products = details.getProducts();
            if(null != products)for(GoodsDetailDTO dto: products){
                ConsignmentDetails detail = new ConsignmentDetails();
                ConsignmentDirection direction = null;
                detail.setConsignment(consignment);
                Product product = productServiceV1.getProductV1ById(dto.getProductId());
                detail.setProduct(product);
                detail.setType(InventoryType.PRODUCT);
                detail.setId(utilsService.generateId(Constants.CONSIGNMENT_DETAIL_PREFIX));
                detail.setComments(details.getComments());
                detail.setCount(dto.getQuantity());
                detail.setSubTotal(new Double(dto.getSubTotal().toString()));
                detail.setTotalAmount(new Double(dto.getTotalAmount().toString()));
                detail.setTaxPercentage(dto.getTaxPercentage());
                detail.setTaxAmount(detail.getTaxAmount());
                detail.setCreatedAt(utilsService.generateDateFormat());
                detail.setIduSerialNo(dto.getIduSerialNo());
                detail.setOduSerialNo(dto.getOduSerialNo());
                if(consignment.getInOut().name().equalsIgnoreCase(InventoryLogEntryV1.IN.name())){
                    direction = ConsignmentDirection.IN;
                }
                else if(consignment.getInOut().name().equalsIgnoreCase(InventoryLogEntryV1.RETURN.name())){
                    direction = ConsignmentDirection.RETURN;
                }
                else{
                    direction = ConsignmentDirection.OUT;
                }
                detail.setDirection(direction);
                consignmentDetailServiceV1.save(detail);
            }
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        return consignment;
    }

    public Consignment createAccessoryEntries(Consignment consignment, ConsignmentDetailsDTO details){

        try{
            List<GoodsDetailDTO> accessories = details.getAccessories();
            if(null != accessories)for(GoodsDetailDTO dto: accessories){
                ConsignmentDetails detail = new ConsignmentDetails();
                ConsignmentDirection direction = null;
                detail.setConsignment(consignment);
                Accessory accessory = accessoryServiceV1.getAccessoryById(dto.getAccessoryId());
                detail.setAccessory(accessory);
                detail.setType(InventoryType.ACCESSORY);
                detail.setId(utilsService.generateId(Constants.CONSIGNMENT_DETAIL_PREFIX));
                detail.setComments(details.getComments());
                detail.setCount(dto.getQuantity());
                detail.setSubTotal(new Double(dto.getSubTotal().toString()));
                detail.setTotalAmount(new Double(dto.getTotalAmount().toString()));
                detail.setTaxPercentage(dto.getTaxPercentage());
                detail.setTaxAmount(detail.getTaxAmount());
                detail.setCreatedAt(utilsService.generateDateFormat());
                if(consignment.getInOut().name().equalsIgnoreCase(InventoryLogEntryV1.IN.name())){
                    direction = ConsignmentDirection.IN;
                }
                else if(consignment.getInOut().name().equalsIgnoreCase(InventoryLogEntryV1.RETURN.name())){
                    direction = ConsignmentDirection.RETURN;
                }
                else{
                    direction = ConsignmentDirection.OUT;
                }
                detail.setDirection(direction);
                consignmentDetailServiceV1.save(detail);
            }
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        return consignment;
    }

    public Consignment createServiceEntries(Consignment consignment, ConsignmentDetailsDTO details){

        try{
            List<GoodsDetailDTO> services = details.getServices();
            if(null != services)for(GoodsDetailDTO dto: services){
                ConsignmentDetails detail = new ConsignmentDetails();
                ConsignmentDirection direction = null;
                detail.setConsignment(consignment);
                Service service = servicesServiceV1.getServiceById(dto.getServiceId());
                detail.setService(service);
                detail.setType(InventoryType.SERVICE);
                detail.setId(utilsService.generateId(Constants.CONSIGNMENT_DETAIL_PREFIX));
                detail.setComments(details.getComments());
                detail.setCount(dto.getQuantity());
                detail.setSubTotal(new Double(dto.getSubTotal().toString()));
                detail.setTotalAmount(new Double(dto.getTotalAmount().toString()));
                detail.setTaxPercentage(dto.getTaxPercentage());
                detail.setTaxAmount(detail.getTaxAmount());
                detail.setCreatedAt(utilsService.generateDateFormat());
                if(consignment.getInOut().name().equalsIgnoreCase(InventoryLogEntryV1.IN.name())){
                    direction = ConsignmentDirection.IN;
                }
                else if(consignment.getInOut().name().equalsIgnoreCase(InventoryLogEntryV1.RETURN.name())){
                    direction = ConsignmentDirection.RETURN;
                }
                else{
                    direction = ConsignmentDirection.OUT;
                }
                detail.setDirection(direction);
                consignmentDetailServiceV1.save(detail);
            }
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        return consignment;
    }

    public void adjustInventory(Consignment consignment, ConsignmentDetailsDTO detail){
        try{
            List<GoodsDetailDTO> products = detail.getProducts();
            Integer quantity = 0;
            if(null != products)for(GoodsDetailDTO dto: products){
                InventoryV1 inventoryV1 = inventoryServiceV1.getInventoryByProduct(dto.getProductId());
                if(consignment.getInOut().name().equalsIgnoreCase(InventoryLogEntryV1.IN.name())){
                    quantity = inventoryV1.getStock() + dto.getQuantity();
                    inventoryV1.setStock(quantity);
                    inventoryServiceV1.saveInventory(inventoryV1);
                }
                else if(consignment.getInOut().name().equalsIgnoreCase(InventoryLogEntryV1.RETURN.name())){
                    quantity = inventoryV1.getStock() + dto.getQuantity();
                    inventoryV1.setStock(quantity);
                    inventoryServiceV1.saveInventory(inventoryV1);
                }
                else{
                    quantity = inventoryV1.getStock() - dto.getQuantity();
                    inventoryV1.setStock(quantity);
                    inventoryServiceV1.saveInventory(inventoryV1);
                }
                quantity = 0;
            }
            List<GoodsDetailDTO> accessories = detail.getAccessories();
            if(null != accessories)for(GoodsDetailDTO dto: accessories){
                InventoryV1 inventoryV1 = inventoryServiceV1.getInventoryByAccessory(dto.getAccessoryId());
                if(consignment.getInOut().name().equalsIgnoreCase(InventoryLogEntryV1.IN.name())){
                    quantity = inventoryV1.getStock() + dto.getQuantity();
                    inventoryV1.setStock(quantity);
                    inventoryServiceV1.saveInventory(inventoryV1);
                }
                else if(consignment.getInOut().name().equalsIgnoreCase(InventoryLogEntryV1.RETURN.name())){
                    quantity = inventoryV1.getStock() + dto.getQuantity();
                    inventoryV1.setStock(quantity);
                    inventoryServiceV1.saveInventory(inventoryV1);
                }
                else{
                    quantity = inventoryV1.getStock() - dto.getQuantity();
                    inventoryV1.setStock(quantity);
                    inventoryServiceV1.saveInventory(inventoryV1);
                }
                quantity = 0;
            }
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public Consignment createInventoryLog(Consignment consignment, ConsignmentDetailsDTO detail){
        try{
            createProductLogs(consignment, detail);
            createAccessoryLogs(consignment, detail);
            createServiceLogs(consignment, detail);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        return consignment;
    }

    private void createProductLogs(Consignment consignment, ConsignmentDetailsDTO detail) {
        List<GoodsDetailDTO> products = detail.getProducts();
        if(null != products)for(GoodsDetailDTO dto: products){
            InventoryLogV1 inventoryLog = new InventoryLogV1();
            UserV1 user = consignment.getCreatedFor();
            String userFirstName = (null != user.getFirstName())?user.getFirstName():" ";
            inventoryLog.setId(utilsService.generateId(Constants.INVENTORY_ORDER_PREFIX));
            inventoryLog.setAmount(dto.getTotalAmount().intValue());
            inventoryLog.setQuantity(dto.getQuantity());
            Product productV1 = productServiceV1.getProductV1ById(dto.getProductId());
            inventoryLog.setProduct(productV1);
            inventoryLog.setIduSerial(dto.getIduSerialNo());
            inventoryLog.setOduSerial(dto.getOduSerialNo());
            if(consignment.getInOut().name().equalsIgnoreCase(InventoryLogEntryV1.IN.name())){
                inventoryLog.setInOut(InventoryLogEntryV1.IN);
                inventoryLog.setComments("Procured " + dto.getQuantity() + " " + productV1.getName() + " (IDU: " + dto.getIduSerialNo() + ", ODU: " + dto.getOduSerialNo() +" ) on " + utilsService.generateDateFormat() + " from " +
                        userFirstName + ", Notes: " + consignment.getComments());
//                publishInwardDetailEvent(inventoryDTO.getQuantity(), product.getName());
            }
            else{
                inventoryLog.setInOut(InventoryLogEntryV1.OUT);
                Product product = productServiceV1.getProductV1ById(dto.getProductId());
                inventoryLog.setComments("Delivered " + dto.getQuantity() + " " + product.getName() + " (IDU: " + dto.getIduSerialNo() + ", ODU: " + dto.getOduSerialNo() +" ) on " + utilsService.generateDateFormat() + " for " +
                        userFirstName + ", Notes: " + consignment.getComments());
            }

            inventoryLog.setCreatedAt(utilsService.generateDateFormat());
            inventoryLog.setConsignment(consignment);
            inventoryLogServiceV1.saveInventoryLog(inventoryLog);
        }
    }

    private void createAccessoryLogs(Consignment consignment, ConsignmentDetailsDTO detail) {
        List<GoodsDetailDTO> accessories = detail.getAccessories();
        if(null != accessories)for(GoodsDetailDTO dto: accessories){
            InventoryLogV1 inventoryLog = new InventoryLogV1();
            UserV1 user = consignment.getCreatedFor();
            String userFirstName = (null != user.getFirstName())?user.getFirstName():" ";
            inventoryLog.setId(utilsService.generateId(Constants.INVENTORY_ORDER_PREFIX));
            inventoryLog.setAmount(dto.getTotalAmount().intValue());
            inventoryLog.setQuantity(dto.getQuantity());
            Accessory accessoryV1 = accessoryServiceV1.getAccessoryById(dto.getAccessoryId());
            inventoryLog.setAccessory(accessoryV1);
            if(consignment.getInOut().name().equalsIgnoreCase(InventoryLogEntryV1.IN.name())){
                inventoryLog.setInOut(InventoryLogEntryV1.IN);
                inventoryLog.setComments("Procured " + dto.getQuantity() + " " + accessoryV1.getName() + " on " + utilsService.generateDateFormat() + " from " +
                        userFirstName + ", Notes: " + consignment.getComments());
//                publishInwardDetailEvent(inventoryDTO.getQuantity(), product.getName());
            }
            else{
                inventoryLog.setInOut(InventoryLogEntryV1.OUT);
                Accessory accessory = accessoryServiceV1.getAccessoryById(dto.getAccessoryId());
                inventoryLog.setComments("Delivered " + dto.getQuantity() + " " + accessory.getName() + " on " + utilsService.generateDateFormat() + " for " +
                        userFirstName + ", Notes: " + consignment.getComments());
            }

            inventoryLog.setCreatedAt(utilsService.generateDateFormat());
            inventoryLog.setConsignment(consignment);
            inventoryLogServiceV1.saveInventoryLog(inventoryLog);
        }
    }

    private void createServiceLogs(Consignment consignment, ConsignmentDetailsDTO detail) {
        List<GoodsDetailDTO> services = detail.getServices();
        if(null != services)for(GoodsDetailDTO dto: services){
            if(consignment.getInOut().name().equalsIgnoreCase(InventoryLogEntryV1.OUT.name())){
                InventoryLogV1 inventoryLog = new InventoryLogV1();
                UserV1 user = consignment.getCreatedFor();
                String userFirstName = (null != user.getFirstName())?user.getFirstName():" ";
                inventoryLog.setId(utilsService.generateId(Constants.INVENTORY_ORDER_PREFIX));
                inventoryLog.setAmount(dto.getTotalAmount().intValue());
                inventoryLog.setQuantity(dto.getQuantity());
                Service service = servicesServiceV1.getServiceById(dto.getServiceId());
                inventoryLog.setService(service);
                if(consignment.getInOut().name().equalsIgnoreCase(InventoryLogEntryV1.OUT.name())){
                    inventoryLog.setInOut(InventoryLogEntryV1.OUT);
                    inventoryLog.setComments("Serviced " + dto.getQuantity() + " " + service.getName() + " on " + utilsService.generateDateFormat() + " for " +
                            userFirstName + ", Notes: " + consignment.getComments());
                }

                inventoryLog.setCreatedAt(utilsService.generateDateFormat());
                inventoryLog.setConsignment(consignment);
                inventoryLogServiceV1.saveInventoryLog(inventoryLog);
            }
        }
    }

    public List<InventoryLogV1> getLogsByConsignment(String consignmentId) {
        Consignment consignment = getConsignmentById(consignmentId);
        return inventoryLogServiceV1.getAllLogsByConsignment(consignment);
    }

    public void undoConsignment(Consignment consignment){
        try{
           List<ConsignmentDetails> details = getConsignmentDetails(consignment.getId());
            Map<String, Integer> deltaMap = new HashMap<>();
            for(ConsignmentDetails detail: details){
                if(detail.getType().equals(InventoryLogEntryV1.IN)){
                    deltaMap.put(detail.getId(), (-1) * detail.getCount());
                }
                else{
                    deltaMap.put(detail.getId(), detail.getCount());
                }
                consignmentDetailServiceV1.delete(detail);
            }
            List<InventoryV1> updatedInventories = new ArrayList<>();
            for(String inventoryId: deltaMap.keySet()){
                InventoryV1 inventory = inventoryServiceV1.getInventoryById(inventoryId);
                Integer currentStock = inventory.getStock();
                currentStock = currentStock + deltaMap.get(inventoryId);
                inventory.setStock(currentStock);
                updatedInventories.add(inventory);
            }
            if(updatedInventories.size() > 0)inventoryServiceV1.saveAll(updatedInventories);
        }
        catch (Exception e){

        }

    }
}
