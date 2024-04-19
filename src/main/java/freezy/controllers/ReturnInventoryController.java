package freezy.controllers;


import freezy.entities.ReturnInventory;
import freezy.services.ReturnInventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/return-inventories")
public class ReturnInventoryController {
    @Autowired
    private ReturnInventoryService returnInventoryService;

    @GetMapping
    public List<ReturnInventory> getAllReturnInventories() {
        return returnInventoryService.getAllReturnInventories();
    }

    @GetMapping("/{id}")
    public ReturnInventory getReturnInventoryById(@PathVariable String id) {
        return returnInventoryService.getReturnInventoryById(id);
    }

    @PostMapping
    public void addReturnInventory(@RequestBody ReturnInventory returnInventory) {
        returnInventoryService.saveReturnInventory(returnInventory);
    }

    @PutMapping("/{id}")
    public void updateReturnInventory(@PathVariable String id, @RequestBody ReturnInventory returnInventory) {
        if (returnInventoryService.getReturnInventoryById(id) != null) {
            returnInventoryService.saveReturnInventory(returnInventory);
        }
    }

    @DeleteMapping("/{id}")
    public void deleteReturnInventory(@PathVariable String id) {
        returnInventoryService.deleteReturnInventory(id);
    }
}
