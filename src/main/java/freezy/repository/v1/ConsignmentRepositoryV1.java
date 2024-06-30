package freezy.repository.v1;

import freezy.entities.v1.CategoryV1;
import freezy.entities.v1.ConsignmentV1;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConsignmentRepositoryV1 extends JpaRepository<ConsignmentV1, String> {
    List<ConsignmentV1> findAllByOrderByCreatedAtDesc();
}
