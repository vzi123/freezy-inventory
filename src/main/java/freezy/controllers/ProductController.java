package freezy.controllers;


import freezy.dto.v1.GoodsDetailDTO;
import freezy.dto.v1.ProductDTOV1;
import freezy.dto.v1.ProductDetailsDTO;
import freezy.entities.Accessory;
import freezy.entities.Category;
import freezy.entities.InventoryType;
import freezy.entities.Product;
import freezy.services.v1.AccessoryServiceV1;
import freezy.services.v1.CategoryServiceV1;
import freezy.services.v1.InventoryServiceV1;
import freezy.services.v1.ProductServiceV1;
import freezy.utils.Constants;
import freezy.utils.UtilsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/products")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ProductController {
    @Autowired
    private ProductServiceV1 productServiceV1;

    @Autowired
    InventoryServiceV1 inventoryServiceV1;

    @Autowired
    UtilsService utilsService;

    @Autowired
    CategoryServiceV1 categoryServiceV1;

    @Autowired
    private AccessoryServiceV1 accessoryServiceV1;

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Product> getAllProducts() {
        return productServiceV1.getAllProducts();
    }

    @GetMapping(value = "/allDetails", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ProductDetailsDTO> getAllProductDetails() {
        return productServiceV1.getAllProductDetails();
    }

    @GetMapping(value= "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Product getProductV1ById(@PathVariable String id) {
        return productServiceV1.getProductV1ById(id);
    }

    @PostMapping(value = "/save",produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object addProduct(@RequestBody ProductDTOV1 productDTO) {
        if(null == productDTO.getCategoryId()){
            return utilsService.sendResponse(Constants.CATEGORY_NULL, HttpStatus.OK);
        }
        else{
            Category category = categoryServiceV1.getCategoryById(productDTO.getCategoryId());
            if(category.getName().equalsIgnoreCase(Constants.ACCESSORIES)){
                Accessory accessory = accessoryServiceV1.saveAccessory(productDTO);
                GoodsDetailDTO goodsDetailDTO = new GoodsDetailDTO();
                goodsDetailDTO.setAccessoryId(accessory.getId());
                goodsDetailDTO.setQuantity(0);
                goodsDetailDTO.setUnitPrice(0);
                goodsDetailDTO.setType(InventoryType.ACCESSORY.name());
                inventoryServiceV1.saveInventory(goodsDetailDTO);
            }
            if(category.getName().equalsIgnoreCase(Constants.PRODUCTS)){
                Product product = productServiceV1.saveProduct(productDTO);
                GoodsDetailDTO dto = new GoodsDetailDTO();
                dto.setProductId(product.getId());
                dto.setType(InventoryType.PRODUCT.name());
                dto.setQuantity(0);
                dto.setUnitPrice(0);
                inventoryServiceV1.saveInventory(dto);
            }

        }
        return null;
    }

    @PutMapping("/{id}")
    public void updateProduct(@PathVariable String id, @RequestBody ProductDTOV1 productDTO) {
        if (productServiceV1.getProductV1ById(id) != null) {
            productServiceV1.saveProduct(productDTO);
        }
    }

    @DeleteMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
    public void deleteProduct(@PathVariable String id) {
        productServiceV1.deleteProduct(id);
    }

    @GetMapping(value = "/filter", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Product> getProductsByCategory(@RequestParam(required = false) String type) {

        return productServiceV1.getProductsByCategory(type);
    }
}
