package freezy.controllers.v1;


import freezy.entities.Category;
import freezy.entities.v1.CategoryV1;
import freezy.services.CategoryService;
import freezy.services.v1.CategoryServiceV1;
import freezy.utils.Constants;
import freezy.utils.FreazyWhatsAppService;
import freezy.utils.StockAlertEmailService;
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
@RequestMapping("/v1/categories")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CategoryControllerV1 {
    @Autowired
    private CategoryServiceV1 categoryServiceV1;

    @Autowired
    FreazyWhatsAppService freazyWhatsAppService;

    @Autowired
    StockAlertEmailService stockAlertEmailService;

    @Autowired
    UtilsService utilsService;

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CategoryV1> getAllCategories() {

        log.info("here");
        return categoryServiceV1.getAllCategories();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CategoryV1 getCategoryById(@PathVariable String id) {
      return categoryServiceV1.getCategoryById(id);

    }

    @PostMapping(value = "/save", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity addCategory(@RequestBody CategoryV1 category) {
        Boolean isValidCategory  = categoryServiceV1.saveCategory(category);
        if(isValidCategory != null && isValidCategory.equals(Boolean.FALSE)){
            return utilsService.sendResponse(Constants.INVALID_CATEGORY, HttpStatus.OK);
        }
        return utilsService.sendResponse(Constants.SUCCESS, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public void updateCategory(@PathVariable String id, @RequestBody CategoryV1 category) {
        if (categoryServiceV1.getCategoryById(id) != null) {
            categoryServiceV1.saveCategory(category);
        }
    }

    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable String id) {
        categoryServiceV1.deleteCategory(id);
    }

    @GetMapping(value = "/send/{message}", produces = MediaType.APPLICATION_JSON_VALUE)
    public void sendWhatsaApp(@PathVariable String message) {
        freazyWhatsAppService.sendMessage(Constants.SEND_SMS, message);
        freazyWhatsAppService.sendMessage(Constants.SEND_SMS2, message);
    }

    @GetMapping(value = "/mail", produces = MediaType.APPLICATION_JSON_VALUE)
    public void sendEmail() throws Exception{
        stockAlertEmailService.checkAndSendEmail();
    }
}
