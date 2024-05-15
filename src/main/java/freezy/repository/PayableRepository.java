package freezy.repository;

import freezy.entities.Payable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PayableRepository extends JpaRepository<Payable, String> {
}
