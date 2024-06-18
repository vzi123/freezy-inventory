package freezy.controllers.v1;


import freezy.dto.v1.ProductDTOV1;
import freezy.entities.Product;
import freezy.entities.v1.ProductV1;
import freezy.services.ProductService;
import freezy.services.v1.ProductServiceV1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/products")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ProductControllerV1 {
    @Autowired
    private ProductServiceV1 productServiceV1;

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ProductV1> getAllProducts() {
        return productServiceV1.getAllProducts();
    }

    @GetMapping(value= "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ProductV1 getProductV1ById(@PathVariable String id) {
        return productServiceV1.getProductV1ById(id);
    }

    @PostMapping(value = "/save",produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
    public void addProduct(@RequestBody ProductDTOV1 productDTO) {
        productServiceV1.saveProduct(productDTO);
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
}
