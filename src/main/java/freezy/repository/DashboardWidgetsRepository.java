package freezy.repository;

import freezy.entities.Category;
import freezy.entities.DashBoardWidgets;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DashboardWidgetsRepository extends JpaRepository<DashBoardWidgets, String> {

    List<DashBoardWidgets> findAllByOrderBySequenceAsc();
}
