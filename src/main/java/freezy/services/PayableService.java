package freezy.services;

import freezy.entities.*;
import freezy.repository.PayableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PayableService {

    @Autowired
    PayableRepository payableRepository;
    public List<Payable> getAllPayables() {
        return payableRepository.findAll();
    }

    public Payable getPayableById(String id) {
        return payableRepository.findById(id).orElse(null);
    }

    public void savePayable(Payable payable) {
        payableRepository.save(payable);
    }

    public void deletePayable(String id) {
        payableRepository.deleteById(id);
    }

    public Payable findBySalesOrder(SalesOrder salesOrder){
        return payableRepository.findBySalesOrder(salesOrder);
    }

    public List<Payable> getPayablesByPurchaseOrder(PurchaseOrder purchaseOrder) {
        List<Payable> payables = new ArrayList<>();
        List<SalesOrder> salesOrders = purchaseOrder.getSalesOrders();
        for(SalesOrder salesOrder: salesOrders){
            Payable payable = findBySalesOrder(salesOrder);
            if(null != payable){
                payables.add(payable);
            }
        }
        return payables;
    }
}
