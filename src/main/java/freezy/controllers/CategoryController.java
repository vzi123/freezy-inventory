package freezy.controllers;


import freezy.entities.Category;
import freezy.services.CategoryService;
import freezy.utils.Constants;
import freezy.utils.FreazyWhatsAppService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/categories")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @Autowired
    FreazyWhatsAppService freazyWhatsAppService;

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Category> getAllCategories() {

        log.info("here");
        return categoryService.getAllCategories();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Category getCategoryById(@PathVariable String id) {
      return categoryService.getCategoryById(id);

    }

    @PostMapping(value = "/save", produces = MediaType.APPLICATION_JSON_VALUE)
    public void addCategory(@RequestBody Category category) {
        categoryService.saveCategory(category);
    }

    @PutMapping("/{id}")
    public void updateCategory(@PathVariable String id, @RequestBody Category category) {
        if (categoryService.getCategoryById(id) != null) {
            categoryService.saveCategory(category);
        }
    }

    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable String id) {
        categoryService.deleteCategory(id);
    }

    @GetMapping(value = "/send/{message}", produces = MediaType.APPLICATION_JSON_VALUE)
    public void sendWhatsaApp(@PathVariable String message) {
        freazyWhatsAppService.sendMessage(Constants.SEND_SMS, message);
        freazyWhatsAppService.sendMessage(Constants.SEND_SMS2, message);
    }
}
