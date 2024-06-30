package freezy.controllers.v1;


import freezy.entities.InventoryLog;
import freezy.entities.v1.InventoryLogV1;
import freezy.services.InventoryLogService;
import freezy.services.v1.InventoryLogServiceV1;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/v1/inventoryLog")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class InventoryLogControllerV1 {
    @Autowired
    private InventoryLogServiceV1 inventoryLogServiceV1;

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<InventoryLogV1> getAllInventoryLogs() {

        log.info("here");
        return inventoryLogServiceV1.getAllInventoryLogs();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public InventoryLogV1 getInventoryLogById(@PathVariable String id) {
      return inventoryLogServiceV1.getInventoryLogById(id);

    }

    @GetMapping(value = "/consignment/{consignmentId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<InventoryLogV1> getLogsByConsignment(@PathVariable String consignmentId) {
        return inventoryLogServiceV1.getAllInventoryLogsByConsignment(consignmentId);
    }
}
