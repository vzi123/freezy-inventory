package freezy.repository;

import freezy.entities.PurchaseOrder;
import freezy.entities.SalesOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalesOrderRepository extends JpaRepository<SalesOrder, String> {
}

