package freezy.controllers;


import freezy.entities.Category_old;
import freezy.services.CategoryService;
import freezy.utils.Constants;
import freezy.utils.FreazyWhatsAppService;
import freezy.utils.StockAlertEmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/categories")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CategoryController_old {
    @Autowired
    private CategoryService categoryService;

    @Autowired
    FreazyWhatsAppService freazyWhatsAppService;

    @Autowired
    StockAlertEmailService stockAlertEmailService;

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Category_old> getAllCategories() {

        log.info("here");
        return categoryService.getAllCategories();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Category_old getCategoryById(@PathVariable String id) {
      return categoryService.getCategoryById(id);

    }

    @PostMapping(value = "/save", produces = MediaType.APPLICATION_JSON_VALUE)
    public void addCategory(@RequestBody Category_old categoryOld) {
        categoryService.saveCategory(categoryOld);
    }

    @PutMapping("/{id}")
    public void updateCategory(@PathVariable String id, @RequestBody Category_old categoryOld) {
        if (categoryService.getCategoryById(id) != null) {
            categoryService.saveCategory(categoryOld);
        }
    }

    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable String id) {
        categoryService.deleteCategory(id);
    }

    @GetMapping(value = "/send/{message}", produces = MediaType.APPLICATION_JSON_VALUE)
    public void sendWhatsaApp(@PathVariable String message) {
        freazyWhatsAppService.sendMessage(Constants.SEND_SMS, message, Constants.WELCOME_TO_FREAZY);
        freazyWhatsAppService.sendMessage(Constants.SEND_SMS2, message, Constants.WELCOME_TO_FREAZY);
    }

    @GetMapping(value = "/mail", produces = MediaType.APPLICATION_JSON_VALUE)
    public void sendEmail() throws Exception{
        stockAlertEmailService.checkAndSendEmail();
    }
}
