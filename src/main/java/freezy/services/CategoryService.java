package freezy.services;



import freezy.entities.Category_old;
import freezy.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category_old> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category_old getCategoryById(String id) {
        return categoryRepository.findById(id).orElse(null);
    }

    public void saveCategory(Category_old categoryOld) {
        categoryRepository.save(categoryOld);
    }

    public void deleteCategory(String id) {
        categoryRepository.deleteById(id);
    }
}
