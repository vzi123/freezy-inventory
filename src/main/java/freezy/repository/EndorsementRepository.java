package freezy.repository;

import freezy.entities.Endorsement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EndorsementRepository extends JpaRepository<Endorsement, String> {
}

