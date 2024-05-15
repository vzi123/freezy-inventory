package freezy.repository;

import freezy.entities.SalesOrderItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SalesOrderItemsRepository extends JpaRepository<SalesOrderItems, String> {

    @Query(value = "select sum(soi.price), sum(soi.quantity),soi.product_id, po.budget, po.id from sales_order_items soi, sales_order so, purchase_order po where po.id = :poId and soi.so_id = so.id and po.id = so.po_id group by po.id, soi.product_id", nativeQuery = true)
    public List getStockAndPriceByPurchaseOrder(@Param("poId") String poId);
}

