package freezy.services;


import freezy.dto.InventoryCountDTO;
import freezy.entities.Inventory;
import freezy.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class InventoryService {
    @Autowired
    private InventoryRepository inventoryRepository;

    public List<Inventory> getAllInventory() {
        return inventoryRepository.findAll();
    }

    public Inventory getInventoryById(String id) {
        return inventoryRepository.findById(id).orElse(null);
    }

    public void saveInventory(Inventory inventory) {
        inventoryRepository.save(inventory);
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
}
