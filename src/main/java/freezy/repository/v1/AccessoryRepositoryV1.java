package freezy.repository.v1;


import freezy.entities.v1.AccessoryV1;
import freezy.entities.v1.CategoryV1;
import freezy.entities.v1.ProductV1;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccessoryRepositoryV1 extends JpaRepository<AccessoryV1, String> {
    List<AccessoryV1> findAllByCategory(CategoryV1 categoryV1);
}

