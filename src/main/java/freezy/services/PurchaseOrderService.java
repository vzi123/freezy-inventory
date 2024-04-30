package freezy.services;


import freezy.entities.PurchaseOrder;
import freezy.repository.PurchaseOrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class PurchaseOrderService {
    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;

    public List<PurchaseOrder> getAllPurchaseOrders() {
        return purchaseOrderRepository.findAll();
    }

    public PurchaseOrder getPurchaseOrderById(String id) {
        return purchaseOrderRepository.findById(id).orElse(null);
    }

    public void savePurchaseOrder(PurchaseOrder purchaseOrder) {
        purchaseOrderRepository.save(purchaseOrder);
    }

    public void deletePurchaseOrder(String id) {
        purchaseOrderRepository.deleteById(id);
    }

    // Other methods as needed
}
