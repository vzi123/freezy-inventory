package freezy.controllers;

import freezy.entities.CategoryUOMMap;
import freezy.services.CategoryUOMMapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category-uom-maps")
public class CategoryUOMMapController {
    @Autowired
    private CategoryUOMMapService categoryUOMMapService;

    @GetMapping
    public List<CategoryUOMMap> getAllCategoryUOMMaps() {
        return categoryUOMMapService.getAllCategoryUOMMaps();
    }

    @GetMapping("/{id}")
    public CategoryUOMMap getCategoryUOMMapById(@PathVariable String id) {
        return categoryUOMMapService.getCategoryUOMMapById(id);
    }

    @PostMapping
    public void addCategoryUOMMap(@RequestBody CategoryUOMMap categoryUOMMap) {
        categoryUOMMapService.saveCategoryUOMMap(categoryUOMMap);
    }

    @PutMapping("/{id}")
    public void updateCategoryUOMMap(@PathVariable String id, @RequestBody CategoryUOMMap categoryUOMMap) {
        if (categoryUOMMapService.getCategoryUOMMapById(id) != null) {
            categoryUOMMapService.saveCategoryUOMMap(categoryUOMMap);
        }
    }

    @DeleteMapping("/{id}")
    public void deleteCategoryUOMMap(@PathVariable String id) {
        categoryUOMMapService.deleteCategoryUOMMap(id);
    }
}
