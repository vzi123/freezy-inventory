package freezy.repository.v1;


import freezy.entities.CategoryUOMMapV1;
import freezy.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryUOMMapRepositoryV1 extends JpaRepository<CategoryUOMMapV1, String> {

    public CategoryUOMMapV1 findByCategory(Category category);
}
