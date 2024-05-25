package freezy.controllers;


import freezy.entities.TransferInventory;
import freezy.services.TransferInventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transfer-inventories")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class TransferInventoryController {
    @Autowired
    private TransferInventoryService transferInventoryService;

    @GetMapping
    public List<TransferInventory> getAllTransferInventories() {
        return transferInventoryService.getAllTransferInventories();
    }

    @GetMapping("/{id}")
    public TransferInventory getTransferInventoryById(@PathVariable String id) {
        return transferInventoryService.getTransferInventoryById(id);
    }

    @PostMapping
    public void addTransferInventory(@RequestBody TransferInventory transferInventory) {
        transferInventoryService.saveTransferInventory(transferInventory);
    }

    @PutMapping("/{id}")
    public void updateTransferInventory(@PathVariable String id, @RequestBody TransferInventory transferInventory) {
        if (transferInventoryService.getTransferInventoryById(id) != null) {
            transferInventoryService.saveTransferInventory(transferInventory);
        }
    }

    @DeleteMapping("/{id}")
    public void deleteTransferInventory(@PathVariable String id) {
        transferInventoryService.deleteTransferInventory(id);
    }
}
