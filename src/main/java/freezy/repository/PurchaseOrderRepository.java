package freezy.repository;

import freezy.entities.Product;
import freezy.entities.PurchaseOrder;
import freezy.entities.PurchaseOrderStatus;
import freezy.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, String> {

    public List<PurchaseOrder> findAllByUserOrderByCreatedAtDesc(User user);
    public List<PurchaseOrder> findAllByUser(User user);


    @Query(value = "select poi.product_id, (poi.quantity) as poQuant from purchase_order po, purchase_order_items poi \n" +
            "    where poi.po_id = po.id and  po.id = :poId", nativeQuery = true)
    public List getGivenStockByPurchaseOrder(@Param("poId") String poId);

    public List<PurchaseOrder> findAllByUserPersona(String persona);
    public List<PurchaseOrder> findAllByUserPersonaAndStatusIn(String persona, List<String> states);
}

