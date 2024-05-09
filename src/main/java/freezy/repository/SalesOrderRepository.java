package freezy.repository;

import freezy.entities.PurchaseOrder;
import freezy.entities.SalesOrder;
import freezy.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SalesOrderRepository extends JpaRepository<SalesOrder, String> {

    List<SalesOrder> findAllByPurchaseOrder(PurchaseOrder purchaseOrder);
    public List<SalesOrder> findAllByUserOrderByCreatedAtDesc(User user);
}

