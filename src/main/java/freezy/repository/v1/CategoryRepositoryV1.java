package freezy.repository.v1;


import freezy.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepositoryV1 extends JpaRepository<Category, String> {

    public Category findByName(String name);
}



