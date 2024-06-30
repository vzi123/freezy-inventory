package freezy.repository.v1;


import freezy.entities.Category;
import freezy.entities.v1.CategoryV1;
import freezy.entities.v1.InventoryLogV1;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepositoryV1 extends JpaRepository<CategoryV1, String> {

    public CategoryV1 findByName(String name);
}



