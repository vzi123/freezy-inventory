package freezy.services.v1;

import freezy.dto.v1.ConsignmentDetailsDTOV1;
import freezy.dto.v1.InventoryDTOV1;
import freezy.dto.v1.InventoryEntryV1;
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

    public ConsignmentDetailsDTOV1 getInventoryLogsByConsignment(String consignmentId){
        ConsignmentV1 consignmentV1 = consignmentRepositoryV1.findById(consignmentId).orElse(null);
        List<InventoryLogV1> logs = inventoryLogRepositoryV1.findAllByConsignment(consignmentV1);

        ConsignmentDetailsDTOV1 entry = new ConsignmentDetailsDTOV1();
        entry.setConsignment(consignmentV1);
        List<ProductV1> productV1s = new ArrayList<>();
        List<AccessoryV1> accessoryV1s = new ArrayList<>();
        List<ServiceV1> serviceV1s = new ArrayList<>();
        List<InventoryLogV1> products = inventoryLogRepositoryV1.findAllByConsignmentAndType(consignmentV1, InventoryTypeV1.PRODUCT);
        List<InventoryLogV1> accessories = inventoryLogRepositoryV1.findAllByConsignmentAndType(consignmentV1, InventoryTypeV1.ACCESSORY);
        List<InventoryLogV1> services = inventoryLogRepositoryV1.findAllByConsignmentAndType(consignmentV1, InventoryTypeV1.SERVICE);
        if(null != products){
            for(InventoryLogV1 log: products){
                productV1s.add(log.getInventory().getProduct());
            }
        }
        if(null != accessories){
            for(InventoryLogV1 log: accessories){
                accessoryV1s.add(log.getInventory().getAccessory());
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



}
