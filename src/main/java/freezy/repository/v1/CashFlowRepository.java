package freezy.repository.v1;

import freezy.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CashFlowRepository extends JpaRepository<Category, String> {

//    public Object getCashFlow();
}
