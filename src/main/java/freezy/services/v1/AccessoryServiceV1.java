package freezy.services.v1;


import freezy.dto.v1.AccessoryDTOV1;
import freezy.dto.v1.ProductDTOV1;
import freezy.entities.v1.AccessoryV1;
import freezy.entities.v1.CategoryV1;
import freezy.entities.v1.ProductV1;
import freezy.repository.v1.AccessoryRepositoryV1;
import freezy.repository.v1.ProductRepositoryV1;
import freezy.utils.Constants;
import freezy.utils.UtilsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AccessoryServiceV1 {
    @Autowired
    private AccessoryRepositoryV1 accessoryRepositoryV1;

    @Autowired
    UtilsService utilsService;

    @Autowired
    CategoryServiceV1 categoryServiceV1;

    @Autowired
    BrandServiceV1 brandServiceV1;


    public List<AccessoryV1> getAllAccessories() {
        return accessoryRepositoryV1.findAll();
    }

    public AccessoryV1 getAccessoryById(String id) {
        return accessoryRepositoryV1.findById(id).orElse(null);
    }

    public AccessoryV1 saveAccessory(AccessoryDTOV1 dto) {
        AccessoryV1 accessory = new AccessoryV1();
        if(null != dto){
            accessory.setId(utilsService.generateId(Constants.ACCESSORY_ORDER_PREFIX));
            accessory.setName(dto.getName());
            accessory.setCategory(categoryServiceV1.getCategoryById(dto.getCategoryId()));
            accessory.setDescription(dto.getDescription());
            if(null != dto.getBrandId())accessory.setBrand(brandServiceV1.getBrandById(dto.getBrandId()));
            if(null != dto.getCost()){
                accessory.setCost(dto.getCost());
            }
            else{
                accessory.setCost(0);
            }
            accessoryRepositoryV1.saveAndFlush(accessory);
        }
        return accessory;
    }

    public AccessoryV1 saveAccessory(ProductDTOV1 dto) {
        AccessoryV1 accessory = new AccessoryV1();
        if(null != dto){
            accessory.setId(utilsService.generateId(Constants.ACCESSORY_ORDER_PREFIX));
            accessory.setName(dto.getName());
            accessory.setCategory(categoryServiceV1.getCategoryById(dto.getCategoryId()));
            accessory.setDescription(dto.getDescription());
            if(null != dto.getBrandId())accessory.setBrand(brandServiceV1.getBrandById(dto.getBrandId()));
            if(null != dto.getCost()){
                accessory.setCost(dto.getCost());
            }
            else{
                accessory.setCost(0);
            }
            accessoryRepositoryV1.saveAndFlush(accessory);
        }
        return accessory;
    }

    public void deleteProduct(String id) {
        accessoryRepositoryV1.deleteById(id);
    }


    // Other methods as needed
}
