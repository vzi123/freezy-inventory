package freezy.repository.v1;


import freezy.entities.Accessory;
import freezy.entities.InventoryV1;
import freezy.entities.Product;
import freezy.entities.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventoryRepositoryV1 extends JpaRepository<InventoryV1, String> {

    public InventoryV1 findByProduct(Product product);
    public InventoryV1 findByAccessory(Accessory accessory);
    public InventoryV1 findByService(Service service);
    public List<InventoryV1> findAllByOrderByIdAsc();
    public List<InventoryV1> findAllByOrderByCreatedAtDesc();
}
