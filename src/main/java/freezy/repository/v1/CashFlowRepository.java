package freezy.repository.v1;

import freezy.entities.v1.CategoryV1;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CashFlowRepository extends JpaRepository<CategoryV1, String> {

//    public Object getCashFlow();
}
