package freezy.repository.v1;


import freezy.entities.Accessory;
import freezy.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccessoryRepositoryV1 extends JpaRepository<Accessory, String> {
    List<Accessory> findAllByCategory(Category category);
}

