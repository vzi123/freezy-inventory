package freezy.controllers;


import freezy.dto.QuotationDTO;
import freezy.dto.QuotationStatusDTO;
import freezy.entities.Category;
import freezy.entities.PurchaseOrder;
import freezy.entities.Quotation;
import freezy.entities.QuotationStatus;
import freezy.services.CategoryService;
import freezy.services.PdfGenerateService;
import freezy.services.PurchaseOrderService;
import freezy.services.QuotationService;
import freezy.utils.Constants;
import freezy.utils.UtilsService;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
    UtilsService utilsService;

    @Autowired
    PdfGenerateService pdfGenerateService;

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
        return Constants.QUOTATION_STATUSES;
    }

    @PostMapping(value = "/status", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object saveStatus(@RequestBody QuotationStatusDTO statusDTO) {

        if (statusDTO.getNewStatus().equalsIgnoreCase(QuotationStatus.APPROVED.name())) {
            Quotation quotationObj = quotationService.getQuotationById(statusDTO.getId());
            if (quotationObj.getQuotationItems().size() > 0) {
                quotationObj.setStatus(statusDTO.getNewStatus());
                quotationService.saveQuotation(quotationObj);
                PurchaseOrder purchaseOrder = purchaseOrderService.createPOFromQuotation(quotationObj);
                quotationObj.setStatus(QuotationStatus.CONVERTED.name());
                return purchaseOrder;
            }
            return utilsService.sendResponse("Cannot Approve Quotation without items", HttpStatus.OK);
        }
        return null;
    }


    @GetMapping(value = "{quotationId}/mail", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object mailQuotation(@PathVariable String quotationId) {
        Quotation quotationObj = quotationService.getQuotationById(quotationId);
        if(quotationObj.getStatus().equalsIgnoreCase(QuotationStatus.DRAFT.name()) || quotationObj.getStatus().equalsIgnoreCase(QuotationStatus.SENT.name())){
            pdfGenerateService.generateQuotation(quotationObj);
            return utilsService.sendResponse("Quotation Mailed", HttpStatus.OK);
        }
        return utilsService.sendResponse("Mail cannot be sent for approved quotations", HttpStatus.OK);
    }


}
