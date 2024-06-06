package freezy.repository;

import freezy.entities.SalesOrderItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SalesOrderItemsRepository extends JpaRepository<SalesOrderItems, String> {

    @Query(value = "select sum(soi.price), sum(soi.quantity),soi.product_id, po.budget, po.id from sales_order_items soi, sales_order so, purchase_order po where po.id = :poId and soi.so_id = so.id and po.id = so.po_id group by po.id, soi.product_id", nativeQuery = true)
    public List getStockAndPriceByPurchaseOrder(@Param("poId") String poId);

    @Query(value = "select sum(soi.price), sum(soi.quantity),soi.product_id, so.id from sales_order_items soi, sales_order so where soi.so_id = so.id and so.id = :soId group by  soi.product_id;", nativeQuery = true)
    public List getStockAndPriceBySalesOrder(@Param("soId") String soId);

    @Query(value = "select soi.product_id, sum(soi.quantity) as soQuant from purchase_order po, sales_order so, sales_order_items soi \n" +
            " where so.po_id = po.id and soi.so_id = so.id and  po.id = :poId  group by soi.product_id;", nativeQuery = true)
    public List getGivenStockBySalesOrderForPurchaseOrder(@Param("poId") String poId);

}

