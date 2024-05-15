package freezy.repository;

import freezy.entities.StockAlerts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockAlertsRepository extends JpaRepository<StockAlerts, String> {
}




