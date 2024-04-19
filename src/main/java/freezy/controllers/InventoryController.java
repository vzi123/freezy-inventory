package freezy.controllers;


import freezy.entities.Inventory;
import freezy.services.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inventory")
public class InventoryController {
    @Autowired
    private InventoryService inventoryService;

    @GetMapping
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

    @DeleteMapping("/{id}")
    public void deleteInventory(@PathVariable String id) {
        inventoryService.deleteInventory(id);
    }
}
