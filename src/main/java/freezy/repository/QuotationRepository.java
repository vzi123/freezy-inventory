package freezy.repository;

import freezy.entities.PurchaseOrder;
import freezy.entities.Quotation;
import freezy.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuotationRepository extends JpaRepository<Quotation, String> {

}

