package freezy.services;

import freezy.entities.PurchaseOrder;
import freezy.entities.PurchaseOrderItems;
import freezy.repository.PurchaseOrderItemsRepository;
import freezy.repository.PurchaseOrderRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@Transactional
public class PurchaseOrderItemsService {
    @Autowired
    private PurchaseOrderItemsRepository purchaseOrderItemsRepository;

    public List<PurchaseOrderItems> getPurchaseOrderItems() {
        return purchaseOrderItemsRepository.findAll();
    }

    public PurchaseOrderItems getPurchaseOrderItemById(String id) {
        return purchaseOrderItemsRepository.findById(id).orElse(null);
    }

    public List<PurchaseOrderItems> getPurchaseOrderItemsByPurchaseOrder(PurchaseOrder purchaseOrder){
        return purchaseOrderItemsRepository.findAllByPurchaseOrder(purchaseOrder);
    }

    public void savePurchaseOrderItems(List<PurchaseOrderItems> items){
        purchaseOrderItemsRepository.saveAllAndFlush(items);
    }

}
