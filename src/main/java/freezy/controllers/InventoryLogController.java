package freezy.controllers;


import freezy.entities.InventoryLogV1;
import freezy.repository.v1.ConsignmentRepositoryV1;
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
public class InventoryLogController {
    @Autowired
    private InventoryLogServiceV1 inventoryLogServiceV1;

    @Autowired
    private ConsignmentRepositoryV1 consignmentRepositoryV1;

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<InventoryLogV1> getAllInventoryLogs() {

        log.info("here");
        return inventoryLogServiceV1.getAllInventoryLogs();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public InventoryLogV1 getInventoryLogById(@PathVariable String id) {
      return inventoryLogServiceV1.getInventoryLogById(id);

    }

//    @GetMapping(value = "/consignment/{consignmentId}", produces = MediaType.APPLICATION_JSON_VALUE)
//    public ConsignmentDetailsDTOV1 getLogsByConsignment(@PathVariable String consignmentId) {
//        return inventoryLogServiceV1.getInventoryLogsByConsignment(consignmentRepositoryV1.findById(consignmentId).get());
//    }
//
//    @GetMapping(value = "/consignments", produces = MediaType.APPLICATION_JSON_VALUE)
//    public List<ConsignmentDetails> getConsignments() {
//        return inventoryLogServiceV1.getAllConsignmentLogs();
//    }

    @GetMapping(value = "/user/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<InventoryLogV1> getLogsByUser(@PathVariable String userId) {
        return inventoryLogServiceV1.getAllLogsByUser(userId);
    }

    @GetMapping(value = "/product/{productId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<InventoryLogV1> getLogsByProduct(@PathVariable String productId) {
        return inventoryLogServiceV1.getLogsByProduct(productId);
    }
}
