package freezy.services;

import freezy.entities.Inventory;
import freezy.entities.InventoryLog;
import freezy.repository.InventoryLogRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryLogService {

    private InventoryLogRepository inventoryLogRepository;
    public List<InventoryLog> getAllInventoryLogs() {
        return inventoryLogRepository.findAll();
    }

    public InventoryLog getInventoryLogById(String id) {
        return inventoryLogRepository.findById(id).orElse(null);
    }

    public void saveInventoryLog(InventoryLog inventoryLog) {
        inventoryLogRepository.save(inventoryLog);
    }

    public void deleteInventoryLog(String id) {
        inventoryLogRepository.deleteById(id);
    }
}
