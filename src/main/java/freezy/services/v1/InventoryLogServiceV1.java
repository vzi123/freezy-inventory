package freezy.services.v1;

import freezy.dto.v1.*;
import freezy.entities.InventoryLog;
import freezy.entities.v1.*;
import freezy.repository.InventoryLogRepository;
import freezy.repository.v1.ConsignmentRepositoryV1;
import freezy.repository.v1.InventoryLogRepositoryV1;
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
        List<ProductDetailsDTO> productV1s = new ArrayList<>();
        List<AccessoryDetailsDTO> accessoryV1s = new ArrayList<>();
        List<ServiceV1> serviceV1s = new ArrayList<>();
        List<InventoryLogV1> products = inventoryLogRepositoryV1.findAllByConsignmentAndType(consignmentV1, InventoryTypeV1.PRODUCT);
        List<InventoryLogV1> accessories = inventoryLogRepositoryV1.findAllByConsignmentAndType(consignmentV1, InventoryTypeV1.ACCESSORY);
        List<InventoryLogV1> services = inventoryLogRepositoryV1.findAllByConsignmentAndType(consignmentV1, InventoryTypeV1.SERVICE);
        if(null != products){
            for(InventoryLogV1 log: products){
                ProductDetailsDTO dto = getProductDetailsDTOfromEntity(log);
                productV1s.add(dto);
            }
        }
        if(null != accessories){
            for(InventoryLogV1 log: accessories){
                AccessoryDetailsDTO dto = getAccessoryDetailsDTOfromEntity(log);
                accessoryV1s.add(dto);
            }
        }
        if(null != services){
            for(InventoryLogV1 log: services){
                serviceV1s.add(log.getInventory().getService());
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

    public AccessoryDetailsDTO getAccessoryDetailsDTOfromEntity(InventoryLogV1 inventoryLogV1){
        AccessoryDetailsDTO detailsDTO = new AccessoryDetailsDTO();
        AccessoryV1 accessoryV1 = inventoryLogV1.getInventory().getAccessory();
        detailsDTO.setId(accessoryV1.getId());
        detailsDTO.setAmount(inventoryLogV1.getAmount().doubleValue());
        detailsDTO.setCategory(accessoryV1.getCategory());
        detailsDTO.setCount(inventoryLogV1.getQuantity());
        detailsDTO.setDescription(accessoryV1.getDescription());
        detailsDTO.setName(accessoryV1.getName());
        detailsDTO.setCost(accessoryV1.getCost());
        detailsDTO.setBrand(accessoryV1.getBrand());
        detailsDTO.setGstPercent(inventoryLogV1.getTaxPercent());
        detailsDTO.setIduSerialNo(inventoryLogV1.getIduSerial());
        detailsDTO.setOduSerialNo(inventoryLogV1.getOduSerial());
        detailsDTO.setSubTotal(inventoryLogV1.getSubTotal());
        detailsDTO.setUnitPrice(inventoryLogV1.getAmount());
        return detailsDTO;
    }

    public ProductDetailsDTO getProductDetailsDTOfromEntity(InventoryLogV1 inventoryLogV1){
        ProductDetailsDTO detailsDTO = new ProductDetailsDTO();
        ProductV1 productV1 = inventoryLogV1.getInventory().getProduct();
        detailsDTO.setId(productV1.getId());
        detailsDTO.setAmount(inventoryLogV1.getAmount().doubleValue());
        detailsDTO.setCategory(productV1.getCategory());
        detailsDTO.setDescription(productV1.getDescription());
        detailsDTO.setName(productV1.getName());
        detailsDTO.setCost(productV1.getCost());
        detailsDTO.setBrand(productV1.getBrand());
        detailsDTO.setGstPercent(inventoryLogV1.getTaxPercent());
        detailsDTO.setIduSerialNo(inventoryLogV1.getIduSerial());
        detailsDTO.setOduSerialNo(inventoryLogV1.getOduSerial());
        detailsDTO.setSubTotal(inventoryLogV1.getSubTotal());
        detailsDTO.setUnitPrice(inventoryLogV1.getAmount());
        return detailsDTO;
    }



}
