package freezy.repository.v1;


import freezy.entities.v1.ServiceTierV1;
import freezy.entities.v1.ServiceV1;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceTierRepositoryV1 extends JpaRepository<ServiceTierV1, String> {

}

