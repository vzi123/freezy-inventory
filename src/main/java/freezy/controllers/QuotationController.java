package freezy.controllers;


import freezy.dto.QuotationDTO;
import freezy.dto.QuotationStatusDTO;
import freezy.entities.PurchaseOrder;
import freezy.entities.Quotation;
import freezy.entities.QuotationStatus;
import freezy.services.*;
import freezy.utils.FreazyConstants;
import freezy.utils.FreazyEmailService;
import freezy.utils.FreazyUtilsService;
import freezy.utils.FreazyPdfGenerateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.io.File;

@RestController
@Slf4j
@RequestMapping("/quotations")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class QuotationController {
    @Autowired
    private QuotationService quotationService;

    @Autowired
    PurchaseOrderService purchaseOrderService;

    @Autowired
    FreazyUtilsService freazyUtilsService;

    @Autowired
    FreazyPdfGenerateService freazyPdfGenerateService;

    @Autowired
    FreazyEmailService freazyEmailService;

    @Autowired
    SalesOrderService salesOrderService;

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Quotation> getAllQuotations() {

        log.info("here");
        return quotationService.getAllQuotations();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Quotation getQuotationById(@PathVariable String id) {
      return quotationService.getQuotationById(id);

    }

    @PostMapping(value = "/save", produces = MediaType.APPLICATION_JSON_VALUE)
    public Quotation addQuotation(@RequestBody QuotationDTO quotationDTO) {

        return quotationService.saveQuotation(quotationDTO);
    }

    @PutMapping("/{id}")
    public Quotation updateQuotation(@PathVariable String id, @RequestBody QuotationDTO quotationDTO) {
        if (quotationService.getQuotationById(id) != null) {
            return quotationService.saveQuotation(quotationDTO);
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public void deleteQuotation(@PathVariable String id) {
        quotationService.deleteQuotation(id);

    }

    @GetMapping(value= "/statuses", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map getQuotationStatuses() {
        return FreazyConstants.QUOTATION_STATUSES;
    }

    @PostMapping(value = "/status", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object saveStatus(@RequestBody QuotationStatusDTO statusDTO) {

        Quotation quotationObj = quotationService.getQuotationById(statusDTO.getId());
        if (statusDTO.getNewStatus().equalsIgnoreCase(QuotationStatus.APPROVED.name()) &&
                !quotationObj.getStatus().equalsIgnoreCase(QuotationStatus.CONVERTED.name())) {
            if (quotationObj.getQuotationItems().size() > 0) {
                quotationObj.setStatus(statusDTO.getNewStatus());
                quotationService.saveQuotation(quotationObj);
                PurchaseOrder purchaseOrder = purchaseOrderService.createPOFromQuotation(quotationObj);
                if(null != statusDTO.getCreateSO() && statusDTO.getCreateSO().booleanValue() == Boolean.TRUE){
                    salesOrderService.clonePOtoSO(purchaseOrder);
                }
                quotationObj.setStatus(QuotationStatus.CONVERTED.name());
                return purchaseOrder;
            }
            return freazyUtilsService.sendResponse("Cannot Approve Quotation without items", HttpStatus.OK);
        }
        /*if(statusDTO.getNewStatus().equalsIgnoreCase(QuotationStatus.DRAFT.name()){
            if (quotationObj.getQuotationItems().size() > 0) {
                quotationObj.setStatus(statusDTO.getNewStatus());
                quotationService.saveQuotation(quotationObj);

            }
            return utilsService.sendResponse("Cannot Approve Quotation without items", HttpStatus.OK);
        }*/
        return null;
    }


    @GetMapping(value = "{quotationId}/mail", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object mailQuotation(@PathVariable String quotationId) throws Exception{
        Quotation quotationObj = quotationService.getQuotationById(quotationId);
        if(quotationObj.getStatus().equalsIgnoreCase(QuotationStatus.DRAFT.name()) || quotationObj.getStatus().equalsIgnoreCase(QuotationStatus.SENT.name())){
            File quotation = freazyPdfGenerateService.generateQuotation(quotationObj);
            freazyEmailService.sendEmail(quotation);
            return freazyUtilsService.sendResponse("Quotation Mailed", HttpStatus.OK);
        }
        return freazyUtilsService.sendResponse("Mail cannot be sent for approved quotations", HttpStatus.OK);
    }


}
