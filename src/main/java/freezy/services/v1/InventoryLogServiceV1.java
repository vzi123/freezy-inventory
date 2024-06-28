package freezy.services.v1;

import freezy.entities.InventoryLog;
import freezy.entities.v1.ConsignmentV1;
import freezy.entities.v1.InventoryLogV1;
import freezy.repository.InventoryLogRepository;
import freezy.repository.v1.InventoryLogRepositoryV1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryLogServiceV1 {

    @Autowired
    private InventoryLogRepositoryV1 inventoryLogRepositoryV1;

    public List<InventoryLogV1> getAllInventoryLogs() {
        return inventoryLogRepositoryV1.findAllByOrderByCreatedAtDesc();
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
