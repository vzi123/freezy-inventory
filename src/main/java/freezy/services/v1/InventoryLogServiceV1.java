package freezy.services.v1;

import freezy.dto.v1.*;
import freezy.entities.InventoryLog;
import freezy.entities.v1.*;
import freezy.repository.InventoryLogRepository;
import freezy.repository.v1.ConsignmentRepositoryV1;
import freezy.repository.v1.InventoryLogRepositoryV1;
import freezy.utils.FreazyUtilsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class InventoryLogServiceV1 {

    @Autowired
    private InventoryLogRepositoryV1 inventoryLogRepositoryV1;

    @Autowired
    ConsignmentRepositoryV1 consignmentRepositoryV1;

    @Autowired
    FreazyUtilsService freazyUtilsService;

    public List<InventoryLogV1> getAllInventoryLogs() {
        return inventoryLogRepositoryV1.findAllByOrderByCreatedAtDesc();
    }

    public List<InventoryLogV1> getAllInventoryLogsByConsignment(String consignmentId) {
        ConsignmentV1 consignmentV1 = consignmentRepositoryV1.findById(consignmentId).orElse(null);
        return inventoryLogRepositoryV1.findAllByConsignment(consignmentV1);
    }

    public List<ConsignmentInfoDTO> getAllConsignmentLogs(){
        List<ConsignmentInfoDTO> consignmentDetails = new ArrayList<>();
        List<ConsignmentV1> consignments = consignmentRepositoryV1.findAllByOrderByCreatedAtDesc();
        for(ConsignmentV1 consignment: consignments){
            ConsignmentInfoDTO info = new ConsignmentInfoDTO();
            info.setId(consignment.getId());
            info.setInOut(consignment.getInOut());
            info.setComments(consignment.getComments());
            info.setCreatedFor(consignment.getCreatedFor());
            info.setCreatedAt(consignment.getCreatedAt());
            info.setItemCount(consignment.getItemCount());
            info.setTotalAmount(consignment.getTotalAmount());
            ConsignmentDetailsDTOV1 detail = getInventoryLogsByConsignment(consignment);
            info.setServices(detail.getServices());
            info.setProducts(detail.getProducts());
            info.setAccessories(detail.getAccessories());
            consignmentDetails.add(info);
        }
        return consignmentDetails;
    }

    public ConsignmentDetailsDTOV1 getInventoryLogsByConsignment(ConsignmentV1 consignmentV1){
//        ConsignmentV1 consignmentV1 = consignmentRepositoryV1.findById(consignmentId).orElse(null);
        List<InventoryLogV1> logs = inventoryLogRepositoryV1.findAllByConsignment(consignmentV1);

        ConsignmentDetailsDTOV1 entry = new ConsignmentDetailsDTOV1();
        entry.setConsignment(consignmentV1);
        List<InventoryDTOV1> productV1s = new ArrayList<>();
        List<InventoryDTOV1> accessoryV1s = new ArrayList<>();
        List<InventoryDTOV1> serviceV1s = new ArrayList<>();
        List<InventoryLogV1> products = inventoryLogRepositoryV1.findAllByConsignmentAndType(consignmentV1, InventoryTypeV1.PRODUCT);
        List<InventoryLogV1> accessories = inventoryLogRepositoryV1.findAllByConsignmentAndType(consignmentV1, InventoryTypeV1.ACCESSORY);
        List<InventoryLogV1> services = inventoryLogRepositoryV1.findAllByConsignmentAndType(consignmentV1, InventoryTypeV1.SERVICE);
        if(null != products){
            for(InventoryLogV1 log: products){
                InventoryDTOV1 dto = getProductDetailsDTOfromEntity(log);
                productV1s.add(dto);
            }
        }
        if(null != accessories){
            for(InventoryLogV1 log: accessories){
                InventoryDTOV1 dto = getAccessoryDetailsDTOfromEntity(log);
                accessoryV1s.add(dto);
            }
        }
        if(null != services){
            for(InventoryLogV1 log: services){
                InventoryDTOV1 dto = getServiceDetailsDTOfromEntity(log);
                serviceV1s.add(dto);
            }
        }

        entry.setServices(serviceV1s);
        entry.setProducts(productV1s);
        entry.setAccessories(accessoryV1s);
        return entry;

    }


    public InventoryLogV1 getInventoryLogById(String id) {
        return inventoryLogRepositoryV1.findById(id).orElse(null);
    }

    public void saveInventoryLog(InventoryLogV1 inventoryLog) {
        inventoryLogRepositoryV1.saveAndFlush(inventoryLog);
    }

    public void deleteInventoryLog(String id) {
        inventoryLogRepositoryV1.deleteById(id);
    }

    public List<InventoryLogV1> getAllLogsByConsignment(ConsignmentV1 consignmentV1){
        return inventoryLogRepositoryV1.findAllByConsignment(consignmentV1);
    }

    public List<InventoryLogV1> getAllLogsByIduSerial(String iduSerial){
        return inventoryLogRepositoryV1.findAllByIduSerial(iduSerial);
    }

    public InventoryDTOV1 getAccessoryDetailsDTOfromEntity(InventoryLogV1 inventoryLogV1){
        AccessoryV1 accessoryV1 = inventoryLogV1.getInventory().getAccessory();
        InventoryDTOV1 dto = new InventoryDTOV1();

//        String productId;
//        String product;
//        String description;
        dto.setDescription(freazyUtilsService.returnDefaultString(accessoryV1.getDescription()));
//        Integer quantity;
        dto.setQuantity(freazyUtilsService.returnDefaultInt(inventoryLogV1.getQuantity()));
//        Integer unitPrice;
        dto.setUnitPrice(freazyUtilsService.returnDefaultInt(inventoryLogV1.getUnitPrice()));
//        Integer discountAmount;
        dto.setDiscountAmount(freazyUtilsService.returnDefaultInt(inventoryLogV1.getDiscountAmount()));
//        Double subTotal;
        dto.setSubTotal(freazyUtilsService.returnDefaultDouble(inventoryLogV1.getSubTotal()));
//        Integer effectivePrice;
        dto.setEffectivePrice(freazyUtilsService.returnDefaultInt(inventoryLogV1.getEffectivePrice()));
//        GSTDTO gstValue;
        GSTDTO gstDTO = new GSTDTO();
        gstDTO.setGstRate(freazyUtilsService.returnDefaultString(inventoryLogV1.getTaxLabel()));
        gstDTO.setGstValue(freazyUtilsService.returnDefaultDouble(inventoryLogV1.getTaxPercent()));
        dto.setGstValue(gstDTO);
//        String iduSerialNo;
        dto.setIduSerialNo(freazyUtilsService.returnDefaultString(inventoryLogV1.getIduSerial()));
//        String oduSerialNo;
        dto.setOduSerialNo(freazyUtilsService.returnDefaultString(inventoryLogV1.getOduSerial()));
//        String type;
        dto.setType(freazyUtilsService.returnDefaultString(inventoryLogV1.getType().name()));
//        String accessoryId;
        dto.setAccessoryId(freazyUtilsService.returnDefaultString(accessoryV1.getId()));
//        String accessory;
        dto.setAccessory(freazyUtilsService.returnDefaultString(accessoryV1.getName()));
//        String serviceId;
//        String service;
//        Double gstPercent;
        return dto;
    }

    public InventoryDTOV1 getProductDetailsDTOfromEntity(InventoryLogV1 inventoryLogV1){
        InventoryDTOV1 dto = new InventoryDTOV1();
        ProductV1 productV1 = inventoryLogV1.getInventory().getProduct();

//        String productId;
        dto.setProductId(freazyUtilsService.returnDefaultString(productV1.getId()));
//        String product;
        dto.setProduct(freazyUtilsService.returnDefaultString(productV1.getName()));
//        String description;
        dto.setDescription(freazyUtilsService.returnDefaultString(productV1.getDescription()));
//        Integer quantity;
        dto.setQuantity(freazyUtilsService.returnDefaultInt(inventoryLogV1.getQuantity()));
//        Integer unitPrice;
        dto.setUnitPrice(freazyUtilsService.returnDefaultInt(inventoryLogV1.getUnitPrice()));
//        Integer discountAmount;
        dto.setDiscountAmount(freazyUtilsService.returnDefaultInt(inventoryLogV1.getDiscountAmount()));
//        Double subTotal;
        dto.setSubTotal(freazyUtilsService.returnDefaultDouble(inventoryLogV1.getSubTotal()));
//        Integer effectivePrice;
        dto.setEffectivePrice(freazyUtilsService.returnDefaultInt(inventoryLogV1.getEffectivePrice()));
//        GSTDTO gstValue;
        GSTDTO gstDTO = new GSTDTO();
        gstDTO.setGstRate(freazyUtilsService.returnDefaultString(inventoryLogV1.getTaxLabel()));
        gstDTO.setGstValue(freazyUtilsService.returnDefaultDouble(inventoryLogV1.getTaxPercent()));
        dto.setGstValue(gstDTO);
//        String iduSerialNo;
        dto.setIduSerialNo(freazyUtilsService.returnDefaultString(inventoryLogV1.getIduSerial()));
//        String oduSerialNo;
        dto.setOduSerialNo(freazyUtilsService.returnDefaultString(inventoryLogV1.getOduSerial()));
//        String type;
        dto.setType(inventoryLogV1.getType().name());
//        String accessoryId;
//        String accessory;
//        String serviceId;
//        String service;
//        Double gstPercent;

        return dto;
    }

    public InventoryDTOV1 getServiceDetailsDTOfromEntity(InventoryLogV1 inventoryLogV1){
        InventoryDTOV1 dto = new InventoryDTOV1();
        ServiceV1 service = inventoryLogV1.getInventory().getService();



//        String productId;
//        String product;
//        String description;
        dto.setDescription(freazyUtilsService.returnDefaultString(service.getDescription()));
//        Integer quantity;
        dto.setQuantity(freazyUtilsService.returnDefaultInt(inventoryLogV1.getQuantity()));
//        Integer unitPrice;
        dto.setUnitPrice(freazyUtilsService.returnDefaultInt(inventoryLogV1.getUnitPrice()));
//        Integer discountAmount;
        dto.setDiscountAmount(freazyUtilsService.returnDefaultInt(inventoryLogV1.getDiscountAmount()));
//        Double subTotal;
        dto.setSubTotal(freazyUtilsService.returnDefaultDouble(inventoryLogV1.getSubTotal()));
//        Integer effectivePrice;
        dto.setEffectivePrice(freazyUtilsService.returnDefaultInt(inventoryLogV1.getEffectivePrice()));
//        GSTDTO gstValue;
        GSTDTO gstDTO = new GSTDTO();
        gstDTO.setGstRate(freazyUtilsService.returnDefaultString(inventoryLogV1.getTaxLabel()));
        gstDTO.setGstValue(freazyUtilsService.returnDefaultDouble(inventoryLogV1.getTaxPercent()));
        dto.setGstValue(gstDTO);
//        String iduSerialNo;
        dto.setIduSerialNo(freazyUtilsService.returnDefaultString(inventoryLogV1.getIduSerial()));
//        String oduSerialNo;
        dto.setOduSerialNo(freazyUtilsService.returnDefaultString(inventoryLogV1.getOduSerial()));
//        String type;
        dto.setType(inventoryLogV1.getType().name());
//        String accessoryId;
//        String accessory;
//        String serviceId;
        dto.setServiceId(freazyUtilsService.returnDefaultString(service.getId()));
//        String service;
        dto.setService(freazyUtilsService.returnDefaultString(service.getName()));
//        Double gstPercent;
        return dto;
    }


}
