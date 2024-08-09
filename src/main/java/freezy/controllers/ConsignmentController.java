package freezy.controllers;


import freezy.dto.v1.ConsignmentDetailsDTO;
import freezy.entities.ConsignmentDetails;
import freezy.entities.Consignment;
import freezy.entities.InventoryLogV1;
import freezy.utils.Constants;
import freezy.utils.PdfGenerateService;
import freezy.services.v1.ConsignmentServiceV1;
import freezy.utils.UtilsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/v1/consignments")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ConsignmentController {
    @Autowired
    private ConsignmentServiceV1 consignmentServiceV1;

    @Autowired
    PdfGenerateService pdfGenerateService;

    @Autowired
    UtilsService utilsService;


    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Consignment> getAllConsignments() {

        log.info("here");
        return consignmentServiceV1.getAllConsignments();
    }

    @GetMapping(value = "/dc/{consignmentId}", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> generateDC(@PathVariable String consignmentId) throws Exception {
        return consignmentServiceV1.generateDC(consignmentId);
    }

    @PostMapping(value = "/inward", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object saveInwardInventory(@RequestBody ConsignmentDetailsDTO dto){
        Boolean isValidIDU = utilsService.validateIDU(dto);
        Boolean isValidQuantity = utilsService.validateQuantity(dto);
        if(isValidIDU != null && isValidIDU.equals(Boolean.FALSE)){
            return utilsService.sendResponse(Constants.INVALID_IDU, HttpStatus.OK);
        }
        if(isValidQuantity != null && isValidQuantity.equals(Boolean.FALSE)){
            return utilsService.sendResponse(Constants.INVALID_QUANTITY_IDU, HttpStatus.OK);
        }
        Consignment consignment = consignmentServiceV1.createConsignmentDetails(dto, Constants.INVENTORY_IN);
//        inwardCreatedPublisher.publishEvent(utilsService.getSuperUser().getId(), consignmentV1.getItemCount());
        return consignment;
    }

    @PostMapping(value = "/outward", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object saveOutwardInventory(@RequestBody ConsignmentDetailsDTO dto) throws Exception {
        Boolean isValidODU = utilsService.validateODU(dto);
        if(isValidODU != null && isValidODU.equals(Boolean.FALSE)){
            return utilsService.sendResponse(Constants.INVALID_ODU, HttpStatus.OK);
        }
        Consignment consignment = consignmentServiceV1.createConsignmentDetails(dto, Constants.INVENTORY_OUT);
//        outwardCreatedPublisher.publishEvent(utilsService.getSuperUser().getId(), consignmentV1.getTotalAmount(), StringUtils.replaceSpaces(consignmentV1.getCreatedFor().getFirst_name()));
        return consignmentServiceV1.generateDC(consignment.getId());
    }

    @GetMapping(value = "/{consignmentId}/logs", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<InventoryLogV1> getConsignmentLogs(@PathVariable String consignmentId) {
        return consignmentServiceV1.getLogsByConsignment(consignmentId);
    }

    @GetMapping(value = "/{consignmentId}/detail", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<ConsignmentDetails> getConsignmentDetails(@PathVariable String consignmentId) {
        return consignmentServiceV1.getConsignmentDetails(consignmentId);
    }

    @PostMapping(value = "/inward/{consignmentId}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object editInwardInventory(@RequestBody ConsignmentDetailsDTO dto, @PathVariable String consignmentId){
        if(null != consignmentId){
            Consignment consignment = consignmentServiceV1.getConsignmentById(consignmentId);
            if(null == consignment){
                return utilsService.sendResponse(Constants.INVALID_CONSIGNMENT, HttpStatus.OK);
            }
            else{
                Boolean isValidIDU = utilsService.validateIDU(dto);
                Boolean isValidQuantity = utilsService.validateQuantity(dto);
                if(isValidIDU != null && isValidIDU.equals(Boolean.FALSE)){
                    return utilsService.sendResponse(Constants.INVALID_IDU, HttpStatus.OK);
                }
                if(isValidQuantity != null && isValidQuantity.equals(Boolean.FALSE)){
                    return utilsService.sendResponse(Constants.INVALID_QUANTITY_IDU, HttpStatus.OK);
                }
            }
            consignmentServiceV1.undoConsignment(consignment);
            Consignment consignmentV1 = consignmentServiceV1.createConsignmentDetails(dto, Constants.INVENTORY_IN);
            return consignmentV1;
        }
        return null;
    }

    @PostMapping(value = "/outward/{consignmentId}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object editOutwardInventory(@RequestBody ConsignmentDetailsDTO dto, @PathVariable String consignmentId) throws Exception{
        if(null != consignmentId){
            Consignment consignmentV1 = consignmentServiceV1.getConsignmentById(consignmentId);
            if(null == consignmentV1){
                return utilsService.sendResponse(Constants.INVALID_CONSIGNMENT, HttpStatus.OK);
            }
            else{
                Boolean isValidIDU = utilsService.validateIDU(dto);
                Boolean isValidQuantity = utilsService.validateQuantity(dto);
                if(isValidIDU != null && isValidIDU.equals(Boolean.FALSE)){
                    return utilsService.sendResponse(Constants.INVALID_IDU, HttpStatus.OK);
                }
                if(isValidQuantity != null && isValidQuantity.equals(Boolean.FALSE)){
                    return utilsService.sendResponse(Constants.INVALID_QUANTITY_IDU, HttpStatus.OK);
                }
            }
            consignmentServiceV1.undoConsignment(consignmentV1);
            Consignment consignment = consignmentServiceV1.createConsignmentDetails(dto, Constants.INVENTORY_OUT);
            return consignment;
        }
        return null;
    }
}
