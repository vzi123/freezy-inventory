package freezy.repository.v1;

import freezy.dto.v1.InventoryEntryV1;
import freezy.entities.InventoryLog;
import freezy.entities.v1.ConsignmentV1;
import freezy.entities.v1.InventoryLogV1;
import freezy.entities.v1.InventoryTypeV1;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventoryLogRepositoryV1 extends JpaRepository<InventoryLogV1, String> {
    List<InventoryLogV1> findAllByOrderByCreatedAtDesc();

    List<InventoryLogV1> findAllByConsignment(ConsignmentV1 consignmentV1);
    List<InventoryLogV1> findAllByType(InventoryTypeV1 type);
    List<InventoryLogV1> findAllByConsignmentAndType(ConsignmentV1 consignmentV1, InventoryTypeV1 type);

    List<InventoryLogV1> findAllByIduSerial(String iduSerial);
}