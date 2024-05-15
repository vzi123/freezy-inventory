package freezy.utils;

import freezy.entities.User;
import freezy.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

@Service
public class UtilsService {

    @Autowired
    UserService userService;

    public String generateId(String prefix){
        return prefix+((long) Math.floor(Math.random() * 9_000_000_000L) + 1_000_000_000L);
    }

    public String generateDateFormat(){
        return new SimpleDateFormat(Constants.DATE_FORMAT).format(new Timestamp(System.currentTimeMillis()));
    }

    public User getSuperUser(){
        return userService.getUserById("U0001");
    }

    public ResponseEntity sendResponse(String message, HttpStatusCode statusCode) {
        ResponseEntity response = new ResponseEntity(message, statusCode);
        return response;
    }
}
