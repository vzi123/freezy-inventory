package freezy.services;

import freezy.entities.Category;
import freezy.entities.Endorsement;
import freezy.repository.CategoryRepository;
import freezy.repository.EndorsementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EndorsementService {

    @Autowired
    private EndorsementRepository endorsementRepository;

    public List<Endorsement> getAllEndorsements() {
        return endorsementRepository.findAll();
    }

    public Endorsement getEndorsementById(String id) {
        return endorsementRepository.findById(id).orElse(null);
    }

    public void saveEndorsement(Endorsement endorsement) {
        endorsementRepository.save(endorsement);
    }

    public void deleteEndorsement(String id) {
        endorsementRepository.deleteById(id);
    }
}
