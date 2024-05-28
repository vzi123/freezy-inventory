package freezy.services;


import freezy.entities.User;
import freezy.repository.UserRepository;
import freezy.utils.Constants;
import freezy.utils.UtilsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(String id) {
        return userRepository.findById(id).orElse(null);
    }

    public User getSuperUser(){
        return getUserById("U0001");
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }

    public List<User> findUsersByQueryParams(Specification<User> spec){
        return userRepository.findAll(spec);
    }

    public List<User> searchUsers(String firstName, String lastName, String phNo, String email){
        return userRepository.searchByParams(firstName, lastName, phNo, email);
    }
}
