package freezy.repository;


import freezy.entities.Inventory;
import freezy.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, String> {

    public Inventory findByProduct(Product product);
}
