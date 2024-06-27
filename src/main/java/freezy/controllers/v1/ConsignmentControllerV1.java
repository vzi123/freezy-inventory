package freezy.controllers.v1;


import freezy.entities.v1.CategoryV1;
import freezy.entities.v1.ConsignmentV1;
import freezy.services.PdfGenerateService;
import freezy.services.v1.CategoryServiceV1;
import freezy.services.v1.ConsignmentServiceV1;
import freezy.utils.Constants;
import freezy.utils.FreazyWhatsAppService;
import freezy.utils.StockAlertEmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.nio.file.Files;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/v1/consignments")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ConsignmentControllerV1 {
    @Autowired
    private ConsignmentServiceV1 consignmentServiceV1;

    @Autowired
    PdfGenerateService pdfGenerateService;


    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ConsignmentV1> getAllConsignments() {

        log.info("here");
        return consignmentServiceV1.getAllConsignments();
    }

    @GetMapping(value = "/dc/{consignmentId}", produces = MediaType.APPLICATION_PDF_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<byte[]> generateDC(@PathVariable String consignmentId) throws Exception {
        File dcFile = pdfGenerateService.generateDeliveryChallan(consignmentServiceV1.getConsignmentById(consignmentId));
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("inline", "document.pdf");

        return new ResponseEntity<>(Files.readAllBytes(dcFile.getAbsoluteFile().toPath()), headers, HttpStatus.OK);
    }
}
