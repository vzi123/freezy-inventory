package freezy.repository;


import freezy.entities.ProjectPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectPlanRepository extends JpaRepository<ProjectPlan, String> {
}
