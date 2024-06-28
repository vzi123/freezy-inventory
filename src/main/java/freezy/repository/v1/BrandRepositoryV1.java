package freezy.repository.v1;

import freezy.entities.v1.BrandV1;
import freezy.entities.v1.CategoryV1;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrandRepositoryV1 extends JpaRepository<BrandV1, String> {

}
