package freezy.services.v1;


import freezy.dto.v1.ProductDTOV1;
import freezy.entities.Product;
import freezy.entities.v1.CategoryV1;
import freezy.entities.v1.ProductV1;
import freezy.repository.v1.ProductRepositoryV1;
import freezy.utils.Constants;
import freezy.utils.UtilsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceV1 {
    @Autowired
    private ProductRepositoryV1 productRepositoryV1;

    @Autowired
    UtilsService utilsService;

    @Autowired
    CategoryServiceV1 categoryServiceV1;

    @Autowired
    BrandServiceV1 brandServiceV1;

    public List<ProductV1> getAllProducts() {
        return productRepositoryV1.findAll();
    }

    public ProductV1 getProductV1ById(String id) {
        return productRepositoryV1.findById(id).orElse(null);
    }

    public ProductV1 saveProduct(ProductDTOV1 dto) {
        ProductV1 productV1 = new ProductV1();
        if(null != dto){
            productV1.setId(utilsService.generateId(Constants.PRODUCT_ORDER_PREFIX));
            productV1.setName(dto.getName());
            productV1.setCategory(categoryServiceV1.getCategoryById(dto.getCategoryId()));
            productV1.setDescription(dto.getDescription());
            productV1.setHsnNo(dto.getHsnNo());
            productV1.setBrand(brandServiceV1.getBrandById(dto.getBrandId()));
            if(null != dto.getCost()){
                productV1.setCost(dto.getCost());
            }
            else{
                productV1.setCost(0);
            }
            productRepositoryV1.saveAndFlush(productV1);
        }
        return productV1;
    }

    public void deleteProduct(String id) {
        productRepositoryV1.deleteById(id);
    }

    public List<ProductV1> getProductsByCategory(String type) {
        List<ProductV1> productV1s = new ArrayList<>();
        CategoryV1 categoryV1 = categoryServiceV1.getCategoriesByType(type);
        if(null != categoryV1){
            productV1s = productRepositoryV1.findAllByCategory(categoryV1);
        }
        return productV1s;
    }

    // Other methods as needed
}
