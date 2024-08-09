package freezy.services.v1;

import freezy.entities.Consignment;
import freezy.entities.InventoryLogV1;
import freezy.repository.v1.ConsignmentRepositoryV1;
import freezy.repository.v1.InventoryLogRepositoryV1;
import freezy.repository.v1.ProductRepositoryV1;
import freezy.repository.v1.UserRepositoryV1;
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
    UserRepositoryV1 userRepositoryV1;

    @Autowired
    ProductRepositoryV1 productRepositoryV1;

    public List<InventoryLogV1> getAllInventoryLogs() {
        return inventoryLogRepositoryV1.findAllByOrderByCreatedAtDesc();
    }

    public List<InventoryLogV1> getAllInventoryLogsByConsignment(String consignmentId) {
        Consignment consignment = consignmentRepositoryV1.findById(consignmentId).orElse(null);
        return inventoryLogRepositoryV1.findAllByConsignment(consignment);
    }
/*
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
        List<ProductV1> productV1s = new ArrayList<>();
        List<AccessoryV1> accessoryV1s = new ArrayList<>();
        List<ServiceV1> serviceV1s = new ArrayList<>();
        List<InventoryLogV1> products = inventoryLogRepositoryV1.findAllByConsignmentAndType(consignmentV1, InventoryTypeV1.PRODUCT);
        List<InventoryLogV1> accessories = inventoryLogRepositoryV1.findAllByConsignmentAndType(consignmentV1, InventoryTypeV1.ACCESSORY);
        List<InventoryLogV1> services = inventoryLogRepositoryV1.findAllByConsignmentAndType(consignmentV1, InventoryTypeV1.SERVICE);
        if(null != products){
            for(InventoryLogV1 log: products){
                productV1s.add(log.getProduct());
            }
        }
        if(null != accessories){
            for(InventoryLogV1 log: accessories){
                accessoryV1s.add(log.getAccessory());
            }
        }
        if(null != services){
            for(InventoryLogV1 log: services){
                serviceV1s.add(log.getService());
            }
        }

        entry.setServices(serviceV1s);
        entry.setProducts(productV1s);
        entry.setAccessories(accessoryV1s);
        return entry;

    }*/


    public InventoryLogV1 getInventoryLogById(String id) {
        return inventoryLogRepositoryV1.findById(id).orElse(null);
    }

    public void saveInventoryLog(InventoryLogV1 inventoryLog) {
        inventoryLogRepositoryV1.saveAndFlush(inventoryLog);
    }

    public void deleteInventoryLog(String id) {
        inventoryLogRepositoryV1.deleteById(id);
    }

    public List<InventoryLogV1> getAllLogsByConsignment(Consignment consignment){
        return inventoryLogRepositoryV1.findAllByConsignment(consignment);
    }

    public List<InventoryLogV1> getAllLogsByIduSerial(String iduSerial){
        return inventoryLogRepositoryV1.findAllByIduSerial(iduSerial);
    }


    public List<InventoryLogV1> getAllLogsByUser(String userId){
        List<Consignment> consignments = consignmentRepositoryV1.findAllByCreatedFor(userRepositoryV1.findById(userId).get());
        return inventoryLogRepositoryV1.findAllByConsignmentIn(consignments);
    }

    public List<InventoryLogV1> getLogsByProduct(String productId){
        return inventoryLogRepositoryV1.findAllByProduct(productRepositoryV1.findById(productId).get());
    }

    public List<String> getIDUsForAProduct(String productId) {
        List<String> idus = new ArrayList<>();
        List<InventoryLogV1> logs =  inventoryLogRepositoryV1.findAllByProduct(productRepositoryV1.findById(productId).get());
        for(InventoryLogV1 log: logs){
            idus.add(log.getIduSerial());
        }
        return idus;
    }

}
