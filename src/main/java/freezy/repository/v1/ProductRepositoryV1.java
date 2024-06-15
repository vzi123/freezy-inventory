package freezy.repository.v1;


import freezy.entities.Product;
import freezy.entities.v1.ProductV1;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepositoryV1 extends JpaRepository<ProductV1, String> {
}

