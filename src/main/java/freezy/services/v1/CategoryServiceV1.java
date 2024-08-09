package freezy.services.v1;



import freezy.entities.Category;
import freezy.repository.v1.CategoryRepositoryV1;
import freezy.utils.Constants;
import freezy.utils.UtilsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceV1 {

    @Autowired
    private CategoryRepositoryV1 categoryRepositoryV1;

    @Autowired
    UtilsService utilsService;

    public List<Category> getAllCategories() {
        return categoryRepositoryV1.findAll();
    }

    public Category getCategoryById(String id) {
        return categoryRepositoryV1.findById(id).orElse(null);
    }

    public Boolean saveCategory(Category category) {
        Category categoryV1 = categoryRepositoryV1.findByName(category.getName());
        if(null != categoryV1)return false;
        category.setId(utilsService.generateId(Constants.CATEGORY_ORDER_PREFIX));
        categoryRepositoryV1.saveAndFlush(category);
        return true;
    }

    public Category getCategoryByType(String type) {
        return categoryRepositoryV1.findByName(type);
    }

    public void deleteCategory(String id) {
        categoryRepositoryV1.deleteById(id);
    }

    public Category getCategoriesByType(String type){
        return categoryRepositoryV1.findByName(type);
    }
}
