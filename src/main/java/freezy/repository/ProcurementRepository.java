package freezy.repository;


import freezy.entities.Procurement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProcurementRepository extends JpaRepository<Procurement, String> {
}
