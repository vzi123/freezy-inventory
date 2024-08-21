package freezy.controllers.v1;


import freezy.entities.v1.ConsignmentV1;
import freezy.utils.FreazyPdfGenerateService;
import freezy.services.v1.ConsignmentServiceV1;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/v1/consignments")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ConsignmentControllerV1 {
    @Autowired
    private ConsignmentServiceV1 consignmentServiceV1;

    @Autowired
    FreazyPdfGenerateService freazyPdfGenerateService;


    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ConsignmentV1> getAllConsignments() {

        log.info("here");
        return consignmentServiceV1.getAllConsignments();
    }

    @GetMapping(value = "/dc/{consignmentId}", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> generateDC(@PathVariable String consignmentId) throws Exception {
        return consignmentServiceV1.generateDC(consignmentId);
    }
}
