package freezy.repository.v1;

import freezy.entities.ConsignmentDetails;
import freezy.entities.Consignment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConsignmentDetailRepositoryV1 extends JpaRepository<ConsignmentDetails, String> {
    List<ConsignmentDetails> findAllByOrderByCreatedAtDesc();

    List<ConsignmentDetails> findAllByConsignment(Consignment consignment);

}
