package freezy.repository.v1;


import freezy.entities.Inventory;
import freezy.entities.Product;
import freezy.entities.v1.InventoryV1;
import freezy.entities.v1.ProductV1;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventoryRepositoryV1 extends JpaRepository<InventoryV1, String> {

    public InventoryV1 findByProduct(ProductV1 product);
    public List<InventoryV1> findAllByOrderByIdAsc();
}
