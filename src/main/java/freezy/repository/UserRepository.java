package freezy.repository;


import freezy.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, String>, JpaSpecificationExecutor<User> {

    @Query(value = "select * from user where (first_name like %:firstName% or phone_number like %:phNo% or last_name like %:lastName% or email like %:email%)"
            , nativeQuery = true)
    public List<User> searchByParams(@Param("firstName") String firstName, @Param("lastName") String lastName,
                                     @Param("phNo") String phNo, @Param("email") String email);
}
