package freezy.services.v1;



import freezy.dto.v1.UOMDTOV1;
import freezy.entities.CategoryUOMMap;
import freezy.entities.v1.CategoryUOMMapV1;
import freezy.entities.v1.UOMV1;
import freezy.repository.CategoryUOMMapRepository;
import freezy.repository.v1.CategoryUOMMapRepositoryV1;
import freezy.utils.Constants;
import freezy.utils.UtilsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryUOMMapServiceV1 {
    @Autowired
    private CategoryUOMMapRepositoryV1 categoryUOMMapRepositoryV1;

    @Autowired
    UtilsService utilsService;

    @Autowired
    CategoryServiceV1 categoryServiceV1;

    public List<CategoryUOMMapV1> getAllCategoryUOMMaps() {
        return categoryUOMMapRepositoryV1.findAll();
    }

    public CategoryUOMMapV1 getCategoryUOMMapById(String id) {
        return categoryUOMMapRepositoryV1.findById(id).orElse(null);
    }

    public void saveCategoryUOMMap(UOMDTOV1 uomdtov1) {
        CategoryUOMMapV1 categoryUOMMap;
        if(null != uomdtov1.getUomId()){
            categoryUOMMap = categoryUOMMapRepositoryV1.findById(uomdtov1.getUomId()).get();
        }
        else{
            categoryUOMMap = new CategoryUOMMapV1();
            categoryUOMMap.setId(utilsService.generateId(Constants.UOM_ORDER_PREFIX));
        }
        categoryUOMMap.setCategory(categoryServiceV1.getCategoryById(uomdtov1.getCategoryId()));
        categoryUOMMap.setUomv1(UOMV1.valueOf(uomdtov1.getUom()));
        categoryUOMMap.setMultiple(uomdtov1.getMultiple());
        categoryUOMMapRepositoryV1.saveAndFlush(categoryUOMMap);
    }

    public void deleteCategoryUOMMap(String id) {
        categoryUOMMapRepositoryV1.deleteById(id);
    }

    public CategoryUOMMapV1 getUOMByCategory(String categoryId){
        return categoryUOMMapRepositoryV1.findByCategory(categoryServiceV1.getCategoryById(categoryId));
    }
}

