package freezy.repository;


import freezy.entities.ReturnInventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReturnInventoryRepository extends JpaRepository<ReturnInventory, String> {
}
