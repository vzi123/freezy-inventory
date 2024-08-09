package freezy.services.v1;


import freezy.dto.v1.ProductDTOV1;
import freezy.dto.v1.ProductDetailsDTO;
import freezy.entities.Category;
import freezy.entities.Product;
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

    @Autowired
    InventoryLogServiceV1 inventoryLogServiceV1;

    public List<Product> getAllProducts() {
        return productRepositoryV1.findAll();
    }

    public List<ProductDetailsDTO> getAllProductDetails() {
        List<ProductDetailsDTO> dtos = new ArrayList<>();
        List<Product> products = productRepositoryV1.findAll();
        for(Product product: products){
            ProductDetailsDTO dto = new ProductDetailsDTO();
            dto.setBrand(product.getBrand());
            dto.setCategory(product.getCategory());
            dto.setCost(product.getCost());
            dto.setDescription(product.getDescription());
            dto.setId(product.getId());
            dto.setName(product.getName());
            dto.setHsnNo(product.getHsnNo());
            List<String> idus = inventoryLogServiceV1.getIDUsForAProduct(product.getId());
            dto.setIdus(idus);
            dtos.add(dto);
        }
        return dtos;
    }

    public Product getProductV1ById(String id) {
        return productRepositoryV1.findById(id).orElse(null);
    }

    public Product saveProduct(ProductDTOV1 dto) {
        Product product = new Product();
        if(null != dto){
            product.setId(utilsService.generateId(Constants.PRODUCT_ORDER_PREFIX));
            product.setName(dto.getName());
            product.setCategory(categoryServiceV1.getCategoryById(dto.getCategoryId()));
            product.setDescription(dto.getDescription());
            product.setHsnNo(dto.getHsnNo());
            product.setBrand(brandServiceV1.getBrandById(dto.getBrandId()));
            if(null != dto.getCost()){
                product.setCost(dto.getCost());
            }
            else{
                product.setCost(0);
            }
            productRepositoryV1.saveAndFlush(product);
        }
        return product;
    }

    public void deleteProduct(String id) {
        productRepositoryV1.deleteById(id);
    }

    public List<Product> getProductsByCategory(String type) {
        List<Product> products = new ArrayList<>();
        Category category = categoryServiceV1.getCategoriesByType(type);
        if(null != category){
            products = productRepositoryV1.findAllByCategory(category);
        }
        return products;
    }

    // Other methods as needed
}
