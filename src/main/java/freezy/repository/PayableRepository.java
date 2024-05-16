package freezy.repository;

import freezy.entities.Payable;
import freezy.entities.SalesOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PayableRepository extends JpaRepository<Payable, String> {


    Payable findBySalesOrder(SalesOrder salesOrder);
}
