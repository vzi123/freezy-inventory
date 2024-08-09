package freezy.utils;

import freezy.dto.v1.ConsignmentDetailsDTO;
import freezy.dto.v1.GoodsDetailDTO;
import freezy.entities.User;
import freezy.entities.InventoryLogV1;
import freezy.entities.UserV1;
import freezy.services.UserService;
import freezy.services.v1.InventoryLogServiceV1;
import freezy.services.v1.UserServiceV1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.TimeZone;


@Service
public class UtilsService {

    @Autowired
    UserService userService;

    @Autowired
    UserServiceV1 userServiceV1;


    @Autowired
    InventoryLogServiceV1 inventoryLogServiceV1;


    public String generateId(String prefix){
        return prefix+((long) Math.floor(Math.random() * 9_000_000_000L) + 1_000_000_000L);
    }

    public static String generateDateFormat(){
        SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.DATE_FORMAT);
        dateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata")); // Set timezone to IST
        String formattedDate = dateFormat.format(new Timestamp(System.currentTimeMillis()));
        return formattedDate;
    }

    public User getSuperUser(){
        return userService.getUserById("U0001");
    }

    public UserV1 getSuperUserV1(){
        return userServiceV1.getUserById("U0001");
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

    public Boolean validateODU(ConsignmentDetailsDTO details) {
        for(GoodsDetailDTO dto: details.getProducts()){
            List<InventoryLogV1> logs = inventoryLogServiceV1.getAllLogsByIduSerial(dto.getSerialNo());
            if(null == logs || logs.size() ==0) return false;
        }
        return true;
    }

    public Boolean validateIDU(ConsignmentDetailsDTO details) {
        for(GoodsDetailDTO dto: details.getProducts()){
            List<InventoryLogV1> logs = inventoryLogServiceV1.getAllLogsByIduSerial(dto.getSerialNo());
            if(null != logs && logs.size() > 0) return false;
        }
        return true;
    }

    public Boolean validateQuantity(ConsignmentDetailsDTO details) {
        for(GoodsDetailDTO dto: details.getProducts()){
            if(null != dto && dto.getQuantity() > 1) return false;
        }
        return true;
    }
}
