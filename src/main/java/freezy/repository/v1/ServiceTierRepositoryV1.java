package freezy.repository.v1;


import freezy.entities.ServiceTier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceTierRepositoryV1 extends JpaRepository<ServiceTier, String> {

}

