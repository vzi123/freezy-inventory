package freezy.services.v1;


import freezy.entities.Brand;
import freezy.repository.v1.BrandRepositoryV1;
import freezy.utils.Constants;
import freezy.utils.UtilsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandServiceV1 {

    @Autowired
    BrandRepositoryV1 brandRepositoryV1;

    @Autowired
    UtilsService utilsService;


    public List<Brand> getAllBrands() {
        return brandRepositoryV1.findAll();
    }

    public Brand getBrandById(String id) {
        return brandRepositoryV1.findById(id).orElse(null);
    }

    public void saveBrand(Brand brand) {
        brand.setId(utilsService.generateId(Constants.BRAND_ORDER_PREFIX));
        brandRepositoryV1.saveAndFlush(brand);
    }
}
