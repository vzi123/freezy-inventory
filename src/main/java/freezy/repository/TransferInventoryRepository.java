package freezy.repository;


import freezy.entities.TransferInventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransferInventoryRepository extends JpaRepository<TransferInventory, String> {
}
