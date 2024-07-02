package freezy.controllers.v1;


import freezy.dto.v1.AccessoryDTOV1;
import freezy.dto.v1.InventoryDTOV1;
import freezy.dto.v1.ProductDTOV1;
import freezy.entities.v1.AccessoryV1;
import freezy.entities.v1.InventoryTypeV1;
import freezy.entities.v1.ProductV1;
import freezy.services.v1.AccessoryServiceV1;
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
@RequestMapping("/v1/accessories")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AccessoryControllerV1 {
    @Autowired
    private AccessoryServiceV1 accessoryServiceV1;

    @Autowired
    InventoryServiceV1 inventoryServiceV1;

    @Autowired
    UtilsService utilsService;

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<AccessoryV1> getAll() {
        return accessoryServiceV1.getAllAccessories();
    }

    @GetMapping(value= "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public AccessoryV1 getById(@PathVariable String id) {
        return accessoryServiceV1.getAccessoryById(id);
    }

    @PostMapping(value = "/save",produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object save(@RequestBody AccessoryDTOV1 dto) {
        if(null == dto.getCategoryId()){
            return utilsService.sendResponse(Constants.CATEGORY_NULL, HttpStatus.OK);
        }
        else{
            AccessoryV1 accessoryV1 = accessoryServiceV1.saveAccessory(dto);
            InventoryDTOV1 inventoryDTOV1 = new InventoryDTOV1();
            inventoryDTOV1.setAccessoryId(accessoryV1.getId());
            inventoryDTOV1.setQuantity(0);
            inventoryDTOV1.setUnitPrice(0);
            inventoryDTOV1.setType(InventoryTypeV1.ACCESSORY.name());
            inventoryServiceV1.saveInventory(inventoryDTOV1);
        }
        return null;
    }

//    @PutMapping("/{id}")
//    public void updateProduct(@PathVariable String id, @RequestBody ProductDTOV1 productDTO) {
//        if (productServiceV1.getProductV1ById(id) != null) {
//            productServiceV1.saveProduct(productDTO);
//        }
//    }

    @DeleteMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
    public void delete(@PathVariable String id) {
        accessoryServiceV1.deleteProduct(id);
    }

//    @GetMapping(value = "/filter", produces = MediaType.APPLICATION_JSON_VALUE)
//    public List<ProductV1> getProductsByCategory(@RequestParam(required = false) String type) {
//
//        return productServiceV1.getProductsByCategory(type);
//    }
}
