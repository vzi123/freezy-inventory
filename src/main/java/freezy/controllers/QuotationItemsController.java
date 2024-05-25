package freezy.controllers;


import freezy.entities.QuotationItems;
import freezy.services.QuotationItemsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/quotationItems")
public class QuotationItemsController {
    @Autowired
    private QuotationItemsService quotationItemsService;

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<QuotationItems> getAllQuotationItems() {

        log.info("here");
        return quotationItemsService.getAllQuotationItems();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public QuotationItems getQuotationItemById(@PathVariable String id) {
      return quotationItemsService.getQuotationItemById(id);

    }

    @PostMapping(value = "/save", produces = MediaType.APPLICATION_JSON_VALUE)
    public void addQuotationItem(@RequestBody QuotationItems quotation) {
        quotationItemsService.saveQuotationItems(quotation);
    }

    @PutMapping("/{id}")
    public void updateQuotationItems(@PathVariable String id, @RequestBody QuotationItems quotationItem) {
        if (quotationItemsService.getQuotationItemById(id) != null) {
            quotationItemsService.saveQuotationItems(quotationItem);

        }
    }

    @DeleteMapping("/{id}")
    public void deleteQuotationItem(@PathVariable String id) {
        quotationItemsService.deleteQuotationItem(id);
    }
}
