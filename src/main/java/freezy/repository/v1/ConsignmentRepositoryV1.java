package freezy.repository.v1;

import freezy.entities.Consignment;
import freezy.entities.UserV1;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConsignmentRepositoryV1 extends JpaRepository<Consignment, String> {
    List<Consignment> findAllByOrderByCreatedAtDesc();
    List<Consignment> findAllByCreatedFor(UserV1 user);
}
