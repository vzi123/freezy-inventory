package freezy.repository;


import freezy.entities.CategoryUOMMap_old;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryUOMMapRepository extends JpaRepository<CategoryUOMMap_old, String> {
}
