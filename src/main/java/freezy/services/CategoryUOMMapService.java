package freezy.services;



import freezy.entities.CategoryUOMMap;
import freezy.repository.CategoryUOMMapRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryUOMMapService {
    @Autowired
    private CategoryUOMMapRepository categoryUOMMapRepository;

    public List<CategoryUOMMap> getAllCategoryUOMMaps() {
        return categoryUOMMapRepository.findAll();
    }

    public CategoryUOMMap getCategoryUOMMapById(String id) {
        return categoryUOMMapRepository.findById(id).orElse(null);
    }

    public void saveCategoryUOMMap(CategoryUOMMap categoryUOMMap) {
        categoryUOMMapRepository.save(categoryUOMMap);
    }

    public void deleteCategoryUOMMap(String id) {
        categoryUOMMapRepository.deleteById(id);
    }
}

