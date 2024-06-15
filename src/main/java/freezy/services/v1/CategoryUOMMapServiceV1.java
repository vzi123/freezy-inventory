package freezy.services.v1;



import freezy.entities.CategoryUOMMap;
import freezy.entities.v1.CategoryUOMMapV1;
import freezy.repository.CategoryUOMMapRepository;
import freezy.repository.v1.CategoryUOMMapRepositoryV1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryUOMMapServiceV1 {
    @Autowired
    private CategoryUOMMapRepositoryV1 categoryUOMMapRepositoryV1;

    public List<CategoryUOMMapV1> getAllCategoryUOMMaps() {
        return categoryUOMMapRepositoryV1.findAll();
    }

    public CategoryUOMMapV1 getCategoryUOMMapById(String id) {
        return categoryUOMMapRepositoryV1.findById(id).orElse(null);
    }

    public void saveCategoryUOMMap(CategoryUOMMapV1 categoryUOMMap) {
        categoryUOMMapRepositoryV1.save(categoryUOMMap);
    }

    public void deleteCategoryUOMMap(String id) {
        categoryUOMMapRepositoryV1.deleteById(id);
    }
}

