package freezy.controllers.v1;


import freezy.entities.v1.BrandV1;
import freezy.entities.v1.CategoryV1;
import freezy.services.v1.BrandServiceV1;
import freezy.services.v1.CategoryServiceV1;
import freezy.utils.Constants;
import freezy.utils.FreazyWhatsAppService;
import freezy.utils.StockAlertEmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/v1/brands")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class BrandControllerV1 {
    @Autowired
    private BrandServiceV1 brandServiceV1;

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<BrandV1> getAllBrands() {

        log.info("here");
        return brandServiceV1.getAllBrands();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public BrandV1 getBrandById(@PathVariable String id) {
      return brandServiceV1.getBrandById(id);

    }

    @PostMapping(value = "/save", produces = MediaType.APPLICATION_JSON_VALUE)
    public void addBrand(@RequestBody BrandV1 brandV1) {
        brandServiceV1.saveBrand(brandV1);
    }

}
