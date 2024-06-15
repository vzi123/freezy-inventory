package freezy.services.v1;



import freezy.entities.v1.CategoryV1;
import freezy.repository.v1.CategoryRepositoryV1;
import freezy.utils.Constants;
import freezy.utils.UtilsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceV1 {

    @Autowired
    private CategoryRepositoryV1 categoryRepositoryV1;

    @Autowired
    UtilsService utilsService;

    public List<CategoryV1> getAllCategories() {
        return categoryRepositoryV1.findAll();
    }

    public CategoryV1 getCategoryById(String id) {
        return categoryRepositoryV1.findById(id).orElse(null);
    }

    public void saveCategory(CategoryV1 category) {
        category.setId(utilsService.generateId(Constants.CATEGORY_ORDER_PREFIX));
        categoryRepositoryV1.saveAndFlush(category);
    }

    public void deleteCategory(String id) {
        categoryRepositoryV1.deleteById(id);
    }
}
