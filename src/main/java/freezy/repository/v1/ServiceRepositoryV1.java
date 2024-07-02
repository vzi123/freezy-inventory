package freezy.repository.v1;


import freezy.entities.v1.AccessoryV1;
import freezy.entities.v1.CategoryV1;
import freezy.entities.v1.ServiceV1;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceRepositoryV1 extends JpaRepository<ServiceV1, String> {

}

