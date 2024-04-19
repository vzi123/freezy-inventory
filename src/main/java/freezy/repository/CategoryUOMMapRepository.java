package freezy.repository;


import freezy.entities.CategoryUOMMap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryUOMMapRepository extends JpaRepository<CategoryUOMMap, String> {
}
