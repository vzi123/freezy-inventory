package freezy.controllers.v1;


import freezy.dto.InventoryCountDTO;
import freezy.dto.v1.InventoryEntryV1;
import freezy.dto.v1.InventoryListV1;
import freezy.entities.v1.ConsignmentV1;
import freezy.entities.v1.InventoryV1;
import freezy.events.InwardCreatedPublisher;
import freezy.events.OutwardCreatedPublisher;
import freezy.services.v1.ConsignmentServiceV1;
import freezy.services.v1.InventoryServiceV1;
import freezy.utils.FreazyConstants;
import freezy.utils.FreazyStringUtils;
import freezy.utils.FreazyUtilsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/inventory")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class InventoryControllerV1 {
    @Autowired
    private InventoryServiceV1 inventoryServiceV1;

    @Autowired
    FreazyUtilsService freazyUtilsService;

    @Autowired
    ConsignmentServiceV1 consignmentServiceV1;

    @Autowired
    InwardCreatedPublisher inwardCreatedPublisher;

    @Autowired
    OutwardCreatedPublisher outwardCreatedPublisher;

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<InventoryListV1> getAllInventory() {
        return inventoryServiceV1.getAllInventory();
    }

    @GetMapping("/{id}")
    public InventoryV1 getInventoryById(@PathVariable String id) {
        return inventoryServiceV1.getInventoryById(id);
    }

    @PostMapping(value = "/save", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void addInventory(@Valid @RequestBody InventoryEntryV1 inventoryEntryV1) {
        inventoryServiceV1.incrementOrDecrementInventory(inventoryEntryV1, null, null);
    }

    @PostMapping(value = "/inward", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object saveInwardInventory(@Valid @RequestBody InventoryEntryV1 inventoryEntryV1){
        Boolean isValidIDU = inventoryServiceV1.validateIDU(inventoryEntryV1);
        Boolean isValidQuantity = inventoryServiceV1.validateQuantity(inventoryEntryV1);
        if(isValidIDU != null && isValidIDU.equals(Boolean.FALSE)){
            return freazyUtilsService.sendResponse(FreazyConstants.INVALID_IDU, HttpStatus.BAD_REQUEST);
        }
        if(isValidQuantity != null && isValidQuantity.equals(Boolean.FALSE)){
            return freazyUtilsService.sendResponse(FreazyConstants.INVALID_QUANTITY_IDU, HttpStatus.BAD_REQUEST);
        }
        ConsignmentV1 consignmentV1 = inventoryServiceV1.incrementOrDecrementInventory(inventoryEntryV1, FreazyConstants.INVENTORY_INC, null);
        inwardCreatedPublisher.publishEvent(freazyUtilsService.getSuperUser().getId(), consignmentV1.getItemCount());
        return consignmentV1;
    }

    @PostMapping(value = "/outward", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object saveOuwardInventory(@Valid @RequestBody InventoryEntryV1 inventoryEntryV1) throws Exception {
        Boolean isValidODU = inventoryServiceV1.validateODU(inventoryEntryV1);
        if(isValidODU != null && isValidODU.equals(Boolean.FALSE)){
            return freazyUtilsService.sendResponse(FreazyConstants.INVALID_ODU, HttpStatus.BAD_REQUEST);
        }
        ConsignmentV1 consignmentV1 = inventoryServiceV1.incrementOrDecrementInventory(inventoryEntryV1, FreazyConstants.INVENTORY_INC, null);
        outwardCreatedPublisher.publishEvent(freazyUtilsService.getSuperUser().getId(), consignmentV1.getTotalAmount(), FreazyStringUtils.replaceSpaces(consignmentV1.getCreatedFor().getFirst_name()));
        return consignmentServiceV1.generateDC(consignmentV1.getId());
    }

//    @GetMapping(value = "/consignment/{consignmentId}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
//    public List<InventoryLogV1> getLogsByConsignment(@PathVariable String consignmentId) {
//        return inventoryServiceV1.getLogsByConsignment(consignmentId);
//    }

//    @PutMapping("/{id}")
//    public void updateInventory(@PathVariable String id, @RequestBody InventoryDTOV1 inventoryDTO) {
//        if (inventoryServiceV1.getInventoryById(id) != null) {
//            inventoryServiceV1.saveInventory(inventoryDTO);
//        }
//    }

    @GetMapping(value = "/count", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<InventoryCountDTO> getInventoryNumbers() {
        return inventoryServiceV1.getInventoryCount();
    }


    @DeleteMapping("/{id}")
    public void deleteInventory(@PathVariable String id) {
        inventoryServiceV1.deleteInventory(id);
    }

    @PostMapping(value = "/inward/{consignmentId}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object editInwardInventory(@Valid @RequestBody InventoryEntryV1 inventoryEntryV1, @PathVariable String consignmentId){
        if(null != consignmentId){
            ConsignmentV1 consignmentV1 = consignmentServiceV1.getConsignmentById(consignmentId);
            if(null == consignmentV1){
                return freazyUtilsService.sendResponse(FreazyConstants.INVALID_CONSIGNMENT, HttpStatus.BAD_REQUEST);
            }
            else{
                Boolean isValidIDU = inventoryServiceV1.validateIDU(inventoryEntryV1);
                Boolean isValidQuantity = inventoryServiceV1.validateQuantity(inventoryEntryV1);
                if(isValidIDU != null && isValidIDU.equals(Boolean.FALSE)){
                    return freazyUtilsService.sendResponse(FreazyConstants.INVALID_IDU, HttpStatus.BAD_REQUEST);
                }
                if(isValidQuantity != null && isValidQuantity.equals(Boolean.FALSE)){
                    return freazyUtilsService.sendResponse(FreazyConstants.INVALID_QUANTITY_IDU, HttpStatus.BAD_REQUEST);
                }
            }
            inventoryServiceV1.undoConsignment(consignmentV1);
            ConsignmentV1 consignment = inventoryServiceV1.incrementOrDecrementInventory(inventoryEntryV1, FreazyConstants.INVENTORY_INC, consignmentV1);
            return consignment;
        }
        return null;
    }

    @PostMapping(value = "/outward/{consignmentId}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object editOutwardInventory(@Valid @RequestBody InventoryEntryV1 inventoryEntryV1, @PathVariable String consignmentId) throws Exception{
        if(null != consignmentId){
            ConsignmentV1 consignmentV1 = consignmentServiceV1.getConsignmentById(consignmentId);
            if(null == consignmentV1){
                return freazyUtilsService.sendResponse(FreazyConstants.INVALID_CONSIGNMENT, HttpStatus.BAD_REQUEST);
            }
            else{
                Boolean isValidODU = inventoryServiceV1.validateODU(inventoryEntryV1);
                if(isValidODU != null && isValidODU.equals(Boolean.FALSE)){
                    return freazyUtilsService.sendResponse(FreazyConstants.INVALID_ODU, HttpStatus.BAD_REQUEST);
                }
            }
            inventoryServiceV1.undoConsignment(consignmentV1);
            ConsignmentV1 consignment = inventoryServiceV1.incrementOrDecrementInventory(inventoryEntryV1, FreazyConstants.INVENTORY_INC, consignmentV1);
            return consignmentServiceV1.generateDC(consignmentV1.getId());
        }
        return null;
    }

}
