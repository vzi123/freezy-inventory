package freezy.repository.v1;


import freezy.entities.Product;
import freezy.entities.v1.CategoryV1;
import freezy.entities.v1.ConsignmentV1;
import freezy.entities.v1.InventoryLogV1;
import freezy.entities.v1.ProductV1;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepositoryV1 extends JpaRepository<ProductV1, String> {
    List<ProductV1> findAllByCategory(CategoryV1 categoryV1);
}

