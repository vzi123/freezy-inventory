package freezy.controllers;


import freezy.entities.Category;
import freezy.services.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/all")
    public List<Category> getAllCategories() {

        log.info("here");
        return categoryService.getAllCategories();
    }

    @GetMapping("/{id}")
    public String getCategoryById(@PathVariable String id) {
        Category category =  categoryService.getCategoryById(id);
        return category.toString();
    }

    @PostMapping
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
}
