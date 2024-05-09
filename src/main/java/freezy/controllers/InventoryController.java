package freezy.controllers;


import freezy.dto.InventoryCountDTO;
import freezy.entities.Inventory;
import freezy.services.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/inventory")
public class InventoryController {
    @Autowired
    private InventoryService inventoryService;

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Inventory> getAllInventory() {
        return inventoryService.getAllInventory();
    }

    @GetMapping("/{id}")
    public Inventory getInventoryById(@PathVariable String id) {
        return inventoryService.getInventoryById(id);
    }

    @PostMapping
    public void addInventory(@RequestBody Inventory inventory) {
        inventoryService.saveInventory(inventory);
    }

    @PutMapping("/{id}")
    public void updateInventory(@PathVariable String id, @RequestBody Inventory inventory) {
        if (inventoryService.getInventoryById(id) != null) {
            inventoryService.saveInventory(inventory);
        }
    }

    @GetMapping(value = "/count", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<InventoryCountDTO> getInventoryNumbers() {
        return inventoryService.getInventoryCount();
    }


    @DeleteMapping("/{id}")
    public void deleteInventory(@PathVariable String id) {
        inventoryService.deleteInventory(id);
    }

}
