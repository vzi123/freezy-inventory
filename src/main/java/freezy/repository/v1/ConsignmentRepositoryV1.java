package freezy.repository.v1;

import freezy.entities.v1.CategoryV1;
import freezy.entities.v1.ConsignmentV1;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConsignmentRepositoryV1 extends JpaRepository<ConsignmentV1, String> {
}
