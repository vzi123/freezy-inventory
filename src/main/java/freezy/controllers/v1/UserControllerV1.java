package freezy.controllers.v1;


import freezy.dto.UserDTO;
import freezy.entities.User;
import freezy.entities.UserRole;
import freezy.entities.v1.UserRoleV1;
import freezy.entities.v1.UserV1;
import freezy.services.UserService;
import freezy.services.v1.UserServiceV1;
import freezy.utils.Constants;
import freezy.utils.UtilsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/v1/users")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserControllerV1 {
    @Autowired
    private UserServiceV1 userServiceV1;

    @Autowired
    UtilsService utilsService;

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<UserV1> getAllUsers() {
        return userServiceV1.getAllUsers();
    }

    @GetMapping("/{id}")
    public UserV1 getUserById(@PathVariable String id) {
        return userServiceV1.getUserById(id);
    }

    @PostMapping(value = "/saveCustomer", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void saveCustomer(@RequestBody UserDTO dto) {
        UserV1 user = new UserV1();
        user.setFirst_name(dto.getName());
        user.setLast_name(dto.getName());
        user.setPhone_number(dto.getPhoneNumber());
        user.setAddress(dto.getAddress());
        user.setEmail(dto.getEmail());
        user.setRole(UserRoleV1.CUSTOMER);
        user.setCity(dto.getCity());
        user.setId(utilsService.generateId(Constants.USER_PREFIX));
        user.setCreated_at(utilsService.generateDateFormat());
        user.setPincode(dto.getPincode());
        user.setGstId(dto.getGstId());
        userServiceV1.saveUser(user);
    }

    @PostMapping(value = "/saveSupplier", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void saveSupplier(@RequestBody UserDTO dto) {
        UserV1 user = new UserV1();
        user.setFirst_name(dto.getName());
        user.setLast_name(dto.getName());
        user.setPhone_number(dto.getPhoneNumber());
        user.setAddress(dto.getAddress());
        user.setEmail(dto.getEmail());
        user.setRole(UserRoleV1.SUPPLIER);
        user.setCity(dto.getCity());
        user.setId(utilsService.generateId(Constants.USER_PREFIX));
        user.setCreated_at(utilsService.generateDateFormat());
        user.setPincode(dto.getPincode());
        user.setGstId(dto.getGstId());
        userServiceV1.saveUser(user);
    }

    @PutMapping("/{id}")
    public void updateUser(@PathVariable String id, @RequestBody UserV1 user) {
        if (userServiceV1.getUserById(id) != null) {
            userServiceV1.saveUser(user);
        }
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable String id) {
        userServiceV1.deleteUser(id);
    }

    @GetMapping("/search")
    public List<UserV1> searchUsers(@RequestParam(required = false) String firstName,
                                                  @RequestParam(required = false) String lastName,
                                                  @RequestParam(required = false) String phoneNo,
                                                  @RequestParam(required = false) String email) {

        log.info("Search request received with params: firstName = {}, lastName = {}, state = {}, isAdmin = {}, from = {}, to = {}"+
                firstName +
                lastName +
                phoneNo +
                email);
        return userServiceV1.searchUsers(firstName, lastName, phoneNo, email);
    }

    @GetMapping(value = "/filter", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<UserV1> filterUsers(@RequestParam(required = false) String type) {

        return userServiceV1.getUsersByType(type);
    }
}
