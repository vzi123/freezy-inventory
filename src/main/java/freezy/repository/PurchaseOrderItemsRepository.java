package freezy.repository;

import freezy.entities.PurchaseOrder;
import freezy.entities.PurchaseOrderItems;
import freezy.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PurchaseOrderItemsRepository extends JpaRepository<PurchaseOrderItems, String> {
    public List<PurchaseOrderItems> findAllByPurchaseOrder(PurchaseOrder purchaseOrder);

    public PurchaseOrderItems findByPurchaseOrderAndProduct(PurchaseOrder purchaseOrder, Product product);
}

