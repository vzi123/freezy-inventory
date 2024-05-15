package freezy.services;

import freezy.entities.Payable;
import freezy.entities.Receivable;
import freezy.repository.PayableRepository;
import freezy.repository.ReceivableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReceivableService {

    @Autowired
    ReceivableRepository receivableRepository;

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
}
