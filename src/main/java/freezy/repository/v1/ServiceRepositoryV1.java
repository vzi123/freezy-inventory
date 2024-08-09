package freezy.repository.v1;


import freezy.entities.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceRepositoryV1 extends JpaRepository<Service, String> {

}

