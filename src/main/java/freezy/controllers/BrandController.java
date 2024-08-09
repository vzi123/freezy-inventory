package freezy.controllers;


import freezy.entities.Brand;
import freezy.services.v1.BrandServiceV1;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/v1/brands")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class BrandController {
    @Autowired
    private BrandServiceV1 brandServiceV1;

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Brand> getAllBrands() {

        log.info("here");
        return brandServiceV1.getAllBrands();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Brand getBrandById(@PathVariable String id) {
      return brandServiceV1.getBrandById(id);

    }

    @PostMapping(value = "/save", produces = MediaType.APPLICATION_JSON_VALUE)
    public void addBrand(@RequestBody Brand brand) {
        brandServiceV1.saveBrand(brand);
    }

}
