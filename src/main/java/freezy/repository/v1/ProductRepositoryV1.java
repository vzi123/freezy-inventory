package freezy.repository.v1;


import freezy.entities.Category;
import freezy.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepositoryV1 extends JpaRepository<Product, String> {
    List<Product> findAllByCategory(Category category);
}

