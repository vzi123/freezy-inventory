package freezy.services;

import freezy.entities.Procurement;
import freezy.repository.ProcurementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProcurementService {
    @Autowired
    private ProcurementRepository procurementRepository;

    public List<Procurement> getAllProcurements() {
        return procurementRepository.findAll();
    }

    public Procurement getProcurementById(String id) {
        return procurementRepository.findById(id).orElse(null);
    }

    public void saveProcurement(Procurement procurement) {
        procurementRepository.save(procurement);
    }

    public void deleteProcurement(String id) {
        procurementRepository.deleteById(id);
    }
}

