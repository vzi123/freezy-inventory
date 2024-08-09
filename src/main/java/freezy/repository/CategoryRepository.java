package freezy.repository;


import freezy.entities.Category_old;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category_old, String> {
}



