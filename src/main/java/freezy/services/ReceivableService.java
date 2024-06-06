package freezy.services;

import freezy.entities.Payable;
import freezy.entities.PurchaseOrder;
import freezy.entities.Receivable;
import freezy.entities.SalesOrder;
import freezy.repository.PayableRepository;
import freezy.repository.ReceivableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReceivableService {

    @Autowired
    ReceivableRepository receivableRepository;

    @Autowired
    PurchaseOrderService purchaseOrderService;

    public List<Receivable> getAllReceivables() {
        return receivableRepository.findAll();
    }

    public Receivable getReceivableById(String id) {
        return receivableRepository.findById(id).orElse(null);
    }

    public void saveReceivable(Receivable receivable) {
        receivableRepository.save(receivable);
    }

    public void deleteReceivable(String id) {
        receivableRepository.deleteById(id);
    }

    public Receivable findBySalesOrder(SalesOrder salesOrder){
        return receivableRepository.findBySalesOrder(salesOrder);
    }

    public List<Receivable> getReceivablesByPurchaseOrder(PurchaseOrder purchaseOrder) {
        List<Receivable> receivables = new ArrayList<>();
        List<SalesOrder> salesOrders = purchaseOrder.getSalesOrders();
        for(SalesOrder salesOrder: salesOrders){
            Receivable receivable = findBySalesOrder(salesOrder);
            if(null != receivable){
                receivables.add(receivable);
            }
        }
        return receivables;
    }
}
