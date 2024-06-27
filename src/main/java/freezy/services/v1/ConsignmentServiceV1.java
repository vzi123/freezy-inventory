package freezy.services.v1;



import freezy.entities.v1.CategoryV1;
import freezy.entities.v1.ConsignmentV1;
import freezy.repository.v1.CategoryRepositoryV1;
import freezy.repository.v1.ConsignmentRepositoryV1;
import freezy.utils.Constants;
import freezy.utils.UtilsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConsignmentServiceV1 {

    @Autowired
    ConsignmentRepositoryV1 consignmentRepositoryV1;

    @Autowired
    UtilsService utilsService;

    public List<ConsignmentV1> getAllConsignments() {
        return consignmentRepositoryV1.findAll();
    }

    public ConsignmentV1 getConsignmentById(String id) {
        return consignmentRepositoryV1.findById(id).orElse(null);
    }

    public void saveConsignment(ConsignmentV1 consignmentV1) {
//        consignmentV1.setId(utilsService.generateId(Constants.CONSIGNMENT_PREFIX));
        consignmentRepositoryV1.saveAndFlush(consignmentV1);
    }

    public void deleteConsignment(String id) {
        consignmentRepositoryV1.deleteById(id);
    }
}
