package freezy.repository;

import freezy.entities.InventoryLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventoryLogRepository extends JpaRepository<InventoryLog, String> {
    List<InventoryLog> findAllByOrderByCreatedAtDesc();
}