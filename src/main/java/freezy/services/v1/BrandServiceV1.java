package freezy.services.v1;


import freezy.entities.v1.BrandV1;
import freezy.repository.v1.BrandRepositoryV1;
import freezy.utils.FreazyConstants;
import freezy.utils.FreazyUtilsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandServiceV1 {

    @Autowired
    BrandRepositoryV1 brandRepositoryV1;

    @Autowired
    FreazyUtilsService freazyUtilsService;


    public List<BrandV1> getAllBrands() {
        return brandRepositoryV1.findAll();
    }

    public BrandV1 getBrandById(String id) {
        return brandRepositoryV1.findById(id).orElse(null);
    }

    public void saveBrand(BrandV1 brandV1) {
        brandV1.setId(freazyUtilsService.generateId(FreazyConstants.BRAND_ORDER_PREFIX));
        brandRepositoryV1.saveAndFlush(brandV1);
    }
}
