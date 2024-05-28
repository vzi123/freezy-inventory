package freezy.repository;

import freezy.entities.Category;
import freezy.entities.DashBoardWidgets;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DashboardWidgetsRepository extends JpaRepository<DashBoardWidgets, String> {
}
