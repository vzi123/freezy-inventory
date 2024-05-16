package freezy.services;

import freezy.entities.Category;
import freezy.entities.Payable;
import freezy.entities.SalesOrder;
import freezy.repository.PayableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PayableService {

    @Autowired
    PayableRepository payableRepository;
    public List<Payable> getAllReceivables() {
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
}
