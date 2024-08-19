package freezy.controllers.v1;


import freezy.entities.v1.ServiceV1;
import freezy.services.v1.InventoryServiceV1;
import freezy.services.v1.ServicesServiceV1;
import freezy.utils.FreazyUtilsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/services")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ServicesControllerV1 {
    @Autowired
    private ServicesServiceV1 servicesServiceV1;

    @Autowired
    InventoryServiceV1 inventoryServiceV1;

    @Autowired
    FreazyUtilsService freazyUtilsService;

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ServiceV1> getAll() {
        return servicesServiceV1.getAllServices();
    }
//
//    @GetMapping(value= "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
//    public AccessoryV1 getById(@PathVariable String id) {
//        return accessoryServiceV1.getAccessoryById(id);
//    }
//
//    @PostMapping(value = "/save",produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
//    public Object save(@RequestBody AccessoryDTOV1 dto) {
//        if(null == dto.getCategoryId()){
//            return utilsService.sendResponse(Constants.CATEGORY_NULL, HttpStatus.OK);
//        }
//        else{
//            AccessoryV1 accessoryV1 = accessoryServiceV1.saveAccessory(dto);
//            InventoryDTOV1 inventoryDTOV1 = new InventoryDTOV1();
//            inventoryDTOV1.setAccessoryId(accessoryV1.getId());
//            inventoryDTOV1.setQuantity(0);
//            inventoryDTOV1.setUnitPrice(0);
//            inventoryDTOV1.setType(InventoryTypeV1.ACCESSORY.name());
//            inventoryServiceV1.saveInventory(inventoryDTOV1);
//        }
//        return null;
//    }
//
////    @PutMapping("/{id}")
////    public void updateProduct(@PathVariable String id, @RequestBody ProductDTOV1 productDTO) {
////        if (productServiceV1.getProductV1ById(id) != null) {
////            productServiceV1.saveProduct(productDTO);
////        }
////    }
//
//    @DeleteMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
//    public void delete(@PathVariable String id) {
//        accessoryServiceV1.deleteProduct(id);
//    }
//
////    @GetMapping(value = "/filter", produces = MediaType.APPLICATION_JSON_VALUE)
////    public List<ProductV1> getProductsByCategory(@RequestParam(required = false) String type) {
////
////        return productServiceV1.getProductsByCategory(type);
////    }
}
