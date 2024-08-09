package freezy.repository.v1;

import freezy.entities.Consignment;
import freezy.entities.Product;
import freezy.entities.InventoryLogV1;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventoryLogRepositoryV1 extends JpaRepository<InventoryLogV1, String> {
    List<InventoryLogV1> findAllByOrderByCreatedAtDesc();

    List<InventoryLogV1> findAllByConsignment(Consignment consignment);
    List<InventoryLogV1> findAllByIduSerial(String iduSerial);

    List<InventoryLogV1> findAllByConsignmentIn(List<Consignment> consignments);
    List<InventoryLogV1> findAllByProduct(Product product);

}