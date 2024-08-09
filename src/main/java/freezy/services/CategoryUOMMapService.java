package freezy.services;



import freezy.entities.CategoryUOMMap_old;
import freezy.repository.CategoryUOMMapRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryUOMMapService {
    @Autowired
    private CategoryUOMMapRepository categoryUOMMapRepository;

    public List<CategoryUOMMap_old> getAllCategoryUOMMaps() {
        return categoryUOMMapRepository.findAll();
    }

    public CategoryUOMMap_old getCategoryUOMMapById(String id) {
        return categoryUOMMapRepository.findById(id).orElse(null);
    }

    public void saveCategoryUOMMap(CategoryUOMMap_old categoryUOMMap) {
        categoryUOMMapRepository.save(categoryUOMMap);
    }

    public void deleteCategoryUOMMap(String id) {
        categoryUOMMapRepository.deleteById(id);
    }
}

