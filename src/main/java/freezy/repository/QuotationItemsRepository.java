package freezy.repository;

import freezy.entities.PurchaseOrder;
import freezy.entities.PurchaseOrderItems;
import freezy.entities.Quotation;
import freezy.entities.QuotationItems;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuotationItemsRepository extends JpaRepository<QuotationItems, String> {
}

