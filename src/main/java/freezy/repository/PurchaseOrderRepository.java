package freezy.repository;

import freezy.entities.Product;
import freezy.entities.PurchaseOrder;
import freezy.entities.PurchaseOrderStatus;
import freezy.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, String> {

    public List<PurchaseOrder> findAllByUserOrderByCreatedAtDesc(User user);
    public List<PurchaseOrder> findAllByUser(User user);
}

