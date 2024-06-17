package freezy.controllers.v1;


import freezy.dto.InventoryCountDTO;
import freezy.dto.InventoryDTO;
import freezy.dto.v1.InventoryEntryV1;
import freezy.dto.v1.InventoryListV1;
import freezy.entities.Inventory;
import freezy.entities.v1.InventoryV1;
import freezy.services.InventoryService;
import freezy.services.v1.InventoryServiceV1;
import freezy.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/inventory")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class InventoryControllerV1 {
    @Autowired
    private InventoryServiceV1 inventoryServiceV1;

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<InventoryListV1> getAllInventory() {
        return inventoryServiceV1.getAllInventory();
    }

    @GetMapping("/{id}")
    public InventoryV1 getInventoryById(@PathVariable String id) {
        return inventoryServiceV1.getInventoryById(id);
    }

    @PostMapping(value = "/save", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void addInventory(@RequestBody InventoryEntryV1 inventoryEntryV1) {
        inventoryServiceV1.incrementOrDecrementInventory(inventoryEntryV1, null);
    }

    @PostMapping(value = "/inward", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void saveInwardInventory(@RequestBody InventoryEntryV1 inventoryEntryV1) {
        inventoryServiceV1.incrementOrDecrementInventory(inventoryEntryV1, Constants.INVENTORY_INC);
    }

    @PostMapping(value = "/outward", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void saveOuwardInventory(@RequestBody InventoryEntryV1 inventoryEntryV1) {
        inventoryServiceV1.incrementOrDecrementInventory(inventoryEntryV1, Constants.INVENTORY_DEDUCT);
    }

    @PutMapping("/{id}")
    public void updateInventory(@PathVariable String id, @RequestBody InventoryDTO inventoryDTO) {
        if (inventoryServiceV1.getInventoryById(id) != null) {
            inventoryServiceV1.saveInventory(inventoryDTO);
        }
    }

    @GetMapping(value = "/count", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<InventoryCountDTO> getInventoryNumbers() {
        return inventoryServiceV1.getInventoryCount();
    }


    @DeleteMapping("/{id}")
    public void deleteInventory(@PathVariable String id) {
        inventoryServiceV1.deleteInventory(id);
    }

}
