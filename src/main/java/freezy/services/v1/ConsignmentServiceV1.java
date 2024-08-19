package freezy.services.v1;



import freezy.entities.v1.ConsignmentV1;
import freezy.repository.v1.ConsignmentRepositoryV1;
import freezy.services.PdfGenerateService;
import freezy.utils.FreazyUtilsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConsignmentServiceV1 {

    @Autowired
    ConsignmentRepositoryV1 consignmentRepositoryV1;

    @Autowired
    FreazyUtilsService freazyUtilsService;

    @Autowired
    PdfGenerateService pdfGenerateService;

    public List<ConsignmentV1> getAllConsignments() {
        return consignmentRepositoryV1.findAllByOrderByCreatedAtDesc();
    }

    public ConsignmentV1 getConsignmentById(String id) {
        return consignmentRepositoryV1.findById(id).orElse(null);
    }

    public void saveConsignment(ConsignmentV1 consignmentV1) {
        consignmentRepositoryV1.saveAndFlush(consignmentV1);
    }

    public void deleteConsignment(String id) {
        consignmentRepositoryV1.deleteById(id);
    }

    public ResponseEntity<byte[]> generateDC(String consignmentId) throws Exception{
        byte[] dcFile = pdfGenerateService.generateDeliveryChallan(getConsignmentById(consignmentId));
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("inline", "document.pdf");
        return new ResponseEntity<>(dcFile, headers, HttpStatus.OK);
    }
}
