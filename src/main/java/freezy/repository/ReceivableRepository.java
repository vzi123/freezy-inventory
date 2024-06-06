package freezy.repository;

import freezy.entities.Payable;
import freezy.entities.Receivable;
import freezy.entities.SalesOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReceivableRepository extends JpaRepository<Receivable, String> {

    Receivable findBySalesOrder(SalesOrder salesOrder);

}
