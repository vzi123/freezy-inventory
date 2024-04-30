package freezy.repository;

import freezy.entities.PurchaseOrder;
import freezy.entities.SalesOrderItems;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalesOrderItemsRepository extends JpaRepository<SalesOrderItems, String> {
}

