package freezy.repository;


import freezy.entities.Inventory;
import freezy.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, String> {

    public Inventory findByProduct(Product product);
    public List<Inventory> findAllByOrderByIdAsc();
}
