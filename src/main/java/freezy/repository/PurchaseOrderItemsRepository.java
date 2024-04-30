package freezy.repository;

import freezy.entities.PurchaseOrder;
import freezy.entities.PurchaseOrderItems;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseOrderItemsRepository extends JpaRepository<PurchaseOrderItems, String> {
}

