package freezy.repository.v1;


import freezy.entities.CategoryUOMMap;
import freezy.entities.v1.CategoryUOMMapV1;
import freezy.entities.v1.CategoryV1;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryUOMMapRepositoryV1 extends JpaRepository<CategoryUOMMapV1, String> {

    public CategoryUOMMapV1 findByCategory(CategoryV1 categoryV1);
}
