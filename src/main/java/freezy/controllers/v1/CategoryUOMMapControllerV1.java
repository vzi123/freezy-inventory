package freezy.controllers.v1;

import freezy.dto.v1.UOMDTOV1;
import freezy.entities.CategoryUOMMap;
import freezy.entities.v1.CategoryUOMMapV1;
import freezy.services.CategoryUOMMapService;
import freezy.services.v1.CategoryUOMMapServiceV1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.print.attribute.standard.Media;
import java.util.List;

@RestController
@RequestMapping("/v1/category-uom-maps")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CategoryUOMMapControllerV1 {
    @Autowired
    private CategoryUOMMapServiceV1 categoryUOMMapServiceV1;

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<CategoryUOMMapV1> getAllCategoryUOMMaps() {
        return categoryUOMMapServiceV1.getAllCategoryUOMMaps();
    }

    @GetMapping("/{id}")
    public CategoryUOMMapV1 getCategoryUOMMapById(@PathVariable String id) {
        return categoryUOMMapServiceV1.getCategoryUOMMapById(id);
    }

    @PostMapping(value = "/save", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void addCategoryUOMMap(@RequestBody UOMDTOV1 uomdtov1) {
        categoryUOMMapServiceV1.saveCategoryUOMMap(uomdtov1);
    }

    @PutMapping("/{id}")
    public void updateCategoryUOMMap(@PathVariable String id, @RequestBody UOMDTOV1 uomdtov1) {
        if (categoryUOMMapServiceV1.getCategoryUOMMapById(id) != null) {
            categoryUOMMapServiceV1.saveCategoryUOMMap(uomdtov1);
        }
    }

    @DeleteMapping("/{id}")
    public void deleteCategoryUOMMap(@PathVariable String id) {
        categoryUOMMapServiceV1.deleteCategoryUOMMap(id);
    }
}
