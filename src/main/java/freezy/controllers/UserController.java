package freezy.controllers;


import freezy.dto.UserDTO;
import freezy.entities.User;
import freezy.entities.UserRole;
import freezy.services.UserService;
import freezy.utils.Constants;
import freezy.utils.UtilsService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/users")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    UtilsService utilsService;

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable String id) {
        return userService.getUserById(id);
    }

    @PostMapping(value = "/saveCustomer", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void saveCustomer(@RequestBody UserDTO dto) {
        User user = new User();
        user.setFirst_name(dto.getName());
        user.setLast_name(dto.getName());
        user.setPhone_number(dto.getPhoneNumber());
        user.setAddress(dto.getAddress());
        user.setRole(UserRole.CUSTOMER);
        user.setCity(dto.getCity());
        user.setId(utilsService.generateId(Constants.USER_PREFIX));
        user.setCreated_at(utilsService.generateDateFormat());
        userService.saveUser(user);
    }

    @PostMapping(value = "/saveSupplier", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void saveSupplier(@RequestBody UserDTO dto) {
        User user = new User();
        user.setFirst_name(dto.getName());
        user.setLast_name(dto.getName());
        user.setPhone_number(dto.getPhoneNumber());
        user.setAddress(dto.getAddress());
        user.setCity(dto.getCity());
        user.setRole(UserRole.SUPPLIER);
        user.setId(utilsService.generateId(Constants.USER_PREFIX));
        user.setCreated_at(utilsService.generateDateFormat());
        userService.saveUser(user);
    }

    @PutMapping("/{id}")
    public void updateUser(@PathVariable String id, @RequestBody User user) {
        if (userService.getUserById(id) != null) {
            userService.saveUser(user);
        }
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable String id) {
        userService.deleteUser(id);
    }

    @GetMapping("/search")
    public List<User> searchUsers(@RequestParam(required = false) String firstName,
                                                  @RequestParam(required = false) String lastName,
                                                  @RequestParam(required = false) String phoneNo,
                                                  @RequestParam(required = false) String email) {

        log.info("Search request received with params: firstName = {}, lastName = {}, state = {}, isAdmin = {}, from = {}, to = {}"+
                firstName +
                lastName +
                phoneNo +
                email);
        return userService.searchUsers(firstName, lastName, phoneNo, email);
    }

    @GetMapping(value = "/filter", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<User> filterUsers(@RequestParam(required = false) String type) {

        return userService.getUsersByType(type);
    }
}
