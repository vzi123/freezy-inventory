package freezy.controllers;

import freezy.dto.FulfillmentDTO;
import freezy.dto.PurchaseOrderDTO;
import freezy.dto.PurchaseOrderDetailsDTO;
import freezy.dto.PurchaseOrderStatusDTO;
import freezy.entities.*;
import freezy.services.PdfGenerateService;
import freezy.services.ProductService;
import freezy.services.PurchaseOrderService;
import freezy.services.UserService;
import freezy.utils.Constants;
import freezy.utils.FreazySMSService;
import freezy.utils.UtilsService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/purchaseOrders")
public class PurchaseOrderController {
    @Autowired
    private PurchaseOrderService purchaseOrderService;

    @Autowired
    private UserService userService;

    @Autowired
    UtilsService utilsService;

    @Autowired
    FreazySMSService freazySMSService;

    @Autowired
    PdfGenerateService pdfGenerateService;

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<PurchaseOrder> getAllPurchaseOrders() {
        List<PurchaseOrder> purchaseOrders = purchaseOrderService.getAllPurchaseOrders();
        return purchaseOrders;
    }

    @GetMapping(value= "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public PurchaseOrderDTO getPurchaseOrderById(@PathVariable String id) {
        PurchaseOrder purchaseOrder = purchaseOrderService.getPurchaseOrderById(id);
        FulfillmentDTO fulfillmentDTO = purchaseOrderService.getFulfillmentDetails(purchaseOrder);
        PurchaseOrderDTO purchaseOrderDTO = new PurchaseOrderDTO();
        purchaseOrderDTO.setPurchaseOrder(purchaseOrder);
        purchaseOrderDTO.setFulfillment(fulfillmentDTO);
        return purchaseOrderDTO;
    }

    @GetMapping(value= "/{id}/fulfill", produces = MediaType.APPLICATION_JSON_VALUE)
    public FulfillmentDTO getFulfillmentDetails(@PathVariable String id) {
        PurchaseOrder purchaseOrder = purchaseOrderService.getPurchaseOrderById(id);
        return purchaseOrderService.getFulfillmentDetails(purchaseOrder);
    }

    @PostMapping(value = "/changeStatus", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public PurchaseOrder changeStatus(@RequestBody PurchaseOrderStatusDTO purchaseOrderStatusDTO){
        PurchaseOrder purchaseOrder = purchaseOrderService.getPurchaseOrderById(purchaseOrderStatusDTO.getId());
         purchaseOrderService.changeStatus(purchaseOrder, purchaseOrderStatusDTO.getOldStatus(), purchaseOrderStatusDTO.getNewStatus());
         return purchaseOrder;
    }

    @PostMapping(value = "/save", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public PurchaseOrder addPurchaseOrder(@RequestBody PurchaseOrder purchaseOrder) {
        log.info(" in post controller");
        purchaseOrderService.savePurchaseOrder(purchaseOrder);
        return purchaseOrderService.getPurchaseOrderById(purchaseOrder.getId());
    }

    @GetMapping(value= "/user/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<PurchaseOrder> getRecentPurchaseOrdersByUser(@PathVariable String userId) {
        User user = userService.getUserById(userId);
        return purchaseOrderService.getRecentPurchaseOrdersByUser(user);
    }

    @PostMapping(value = "/saveDetails", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object savePurchaseOrderDetails(@RequestBody PurchaseOrderDetailsDTO purchaseOrderDetails) {
        log.info(" in post controller");
        PurchaseOrder purchaseOrder = purchaseOrderService.savePurchaseOrderDetails(purchaseOrderDetails);
        freazySMSService.sendSms(Constants.SEND_SMS, utilsService.generatePOMessage(purchaseOrder.getId()));
        return purchaseOrder;
    }

    @GetMapping(value= "/{id}/products", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Product> getProductsInPurchaseOrder(@PathVariable String id) {
        PurchaseOrder purchaseOrder = purchaseOrderService.getPurchaseOrderById(id);
        List<Product> products = new ArrayList<>();
        for(PurchaseOrderItems item: purchaseOrder.getPurchaseOrderItems()){
            products.add(item.getProduct());
        }
        return products;
    }

    @GetMapping(value= "/statuses/{persona}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map getPurchaseOrderStatuses(@PathVariable String persona) {
        if(null != persona && persona.equalsIgnoreCase(Constants.CUSTOMER)){
            return Constants.PO_STATUSES_CUSTOMER;
        }
        if(null != persona && persona.equalsIgnoreCase(Constants.VENDOR)){
            return Constants.PO_STATUSES_SUPPLIER;
        }
        return null;
    }

    @GetMapping(value = "/all/{persona}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<PurchaseOrder> getAllPurchaseOrders(@PathVariable String persona, @RequestParam(required = false) String state) {
        String[] states = null;
        if(null != state && state.length() > 0){
            states = state.split(",");
        }
        List<String> orderStates = Arrays.asList(states);
        List<PurchaseOrder> purchaseOrders = purchaseOrderService.getAllPurchaseOrdersByPersonaAndStates(persona, orderStates);
        return purchaseOrders;
    }

}
