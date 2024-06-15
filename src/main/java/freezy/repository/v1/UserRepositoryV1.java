package freezy.repository.v1;


import freezy.entities.User;
import freezy.entities.UserRole;
import freezy.entities.v1.UserRoleV1;
import freezy.entities.v1.UserV1;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepositoryV1 extends JpaRepository<UserV1, String>, JpaSpecificationExecutor<UserV1> {

    @Query(value = "select * from user_v1 where (first_name like %:firstName% or phone_number like %:phNo% or last_name like %:lastName% or email like %:email%)"
            , nativeQuery = true)
    public List<UserV1> searchByParams(@Param("firstName") String firstName, @Param("lastName") String lastName,
                                     @Param("phNo") String phNo, @Param("email") String email);

    public List<UserV1> findAllByRole(UserRoleV1 userRole);
}
