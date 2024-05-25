package freezy.controllers;


import freezy.entities.Category;
import freezy.entities.InventoryLog;
import freezy.services.CategoryService;
import freezy.services.InventoryLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/inventoryLog")
public class InventoryLogController {
    @Autowired
    private InventoryLogService inventoryLogService;

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<InventoryLog> getAllInventoryLogs() {

        log.info("here");
        return inventoryLogService.getAllInventoryLogs();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public InventoryLog getInventoryLogById(@PathVariable String id) {
      return inventoryLogService.getInventoryLogById(id);

    }
}
