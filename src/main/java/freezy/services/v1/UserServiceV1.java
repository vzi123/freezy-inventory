package freezy.services.v1;
import freezy.entities.UserRole;
import freezy.entities.v1.UserRoleV1;
import freezy.entities.v1.UserV1;
import freezy.repository.v1.UserRepositoryV1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceV1 {
    @Autowired
    private UserRepositoryV1 userRepositoryV1;

    public List<UserV1> getAllUsers() {
        return userRepositoryV1.findAll();
    }

    public UserV1 getUserById(String id) {
        return userRepositoryV1.findById(id).orElse(null);
    }

    public UserV1 getSuperUser(){
        return getUserById("U0001");
    }

    public void saveUser(UserV1 user) {
        userRepositoryV1.save(user);
    }

    public void deleteUser(String id) {
        userRepositoryV1.deleteById(id);
    }

    public List<UserV1> findUsersByQueryParams(Specification<UserV1> spec){
        return userRepositoryV1.findAll(spec);
    }

    public List<UserV1> searchUsers(String firstName, String lastName, String phNo, String email){
        return userRepositoryV1.searchByParams(firstName, lastName, phNo, email);
    }

    public List<UserV1> getUsersByType(String type) {
        UserRoleV1 role = null;
        if(null != type && type.equalsIgnoreCase(UserRole.CUSTOMER.name())){
            role = UserRoleV1.CUSTOMER;
        }
        if(null != type && type.equalsIgnoreCase(UserRole.SUPPLIER.name())){
            role = UserRoleV1.SUPPLIER;
        }
        if(null != role){
            return userRepositoryV1.findAllByRole(role);
        }
        return null;
    }
}
