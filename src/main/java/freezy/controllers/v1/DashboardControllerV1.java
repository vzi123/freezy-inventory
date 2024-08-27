package freezy.controllers.v1;


import freezy.dto.v1.InventoryListV1;
import freezy.entities.v1.InventoryLogV1;
import freezy.services.v1.InventoryLogServiceV1;
import freezy.services.v1.InventoryServiceV1;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("v1/dashboard")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class DashboardControllerV1 {

    @Autowired
    private InventoryLogServiceV1 inventoryLogServiceV1;

    @Autowired
    private InventoryServiceV1 inventoryServiceV1;

    @GetMapping(value = "/inventoryLog", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<InventoryLogV1> getAllInventoryLogs() {
        return inventoryLogServiceV1.getAllInventoryLogs();
    }

    @GetMapping(value = "/stock", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<InventoryListV1> getAllInventory() {
        return inventoryServiceV1.getAllInventory();
    }
}
