package freezy.utils;

import freezy.entities.User;
import freezy.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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

    public String generatePOMessage(String poId){
        return "A new PO with id " + poId + " has been created. Please login into Freazy to see the details.";
    }

    public String generatePayableMessage(String payable){
        return "A new payable with amount " + payable + " has been created. Please login into Freazy to see the details.";
    }

    public String generateReceivableMessage(String payable){
        return "A new receivable with amount " + payable + " has been created. Please login into Freazy to see the details.";
    }

    public String generateSOMessage(String soId){
        return "A new SO with id " + soId + " has been created. Please login into Freazy to see the details.";
    }

    public String generateQuoToPOMessage(String quoId, String poId){
        return "A quotation with id " + quoId +" is converted to a new PO with id " + poId + " has been created. Please login into Freazy to see the details.";
    }

//    private static final BigDecimal ONE_HUNDRED = new BigDecimal("100");
//
//    public BigDecimal toPercentageOf(BigDecimal value, BigDecimal total) {
//        return value.divide(total, 4, RoundingMode.HALF_UP).multiply(ONE_HUNDRED);
//    }
}
