package freezy.services;

import freezy.entities.PurchaseOrder;
import freezy.entities.SalesOrder;
import freezy.entities.User;
import freezy.repository.SalesOrderRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@Transactional
public class SalesOrderService {
    @Autowired
    private SalesOrderRepository salesOrderRepository;

    public List<SalesOrder> getAllSalesOrders() {
        return salesOrderRepository.findAll();
    }

    public SalesOrder getSalesOrderById(String id) {
        return salesOrderRepository.findById(id).orElse(null);
    }

    public List<SalesOrder> getAllSalesOrdersByPurchaseOrder(PurchaseOrder purchaseOrder){
        return salesOrderRepository.findAllByPurchaseOrder(purchaseOrder);
    }

    public List<SalesOrder> getRecentSalesOrdersByUser(User user){
        return salesOrderRepository.findAllByUserOrderByCreatedAtDesc(user);
    }

}
