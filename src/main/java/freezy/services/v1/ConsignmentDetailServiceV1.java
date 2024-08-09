package freezy.services.v1;



import freezy.entities.ConsignmentDetails;
import freezy.entities.Consignment;
import freezy.repository.v1.ConsignmentDetailRepositoryV1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConsignmentDetailServiceV1 {

    @Autowired
    ConsignmentDetailRepositoryV1 consignmentDetailRepositoryV1;

    public List<ConsignmentDetails> getDetailsByConsignment(Consignment consignment) {
        return consignmentDetailRepositoryV1.findAllByConsignment(consignment);
    }

    public void save(ConsignmentDetails details){
        consignmentDetailRepositoryV1.saveAndFlush(details);
    }

    public void delete(ConsignmentDetails details){
        consignmentDetailRepositoryV1.delete(details);
    }

}
