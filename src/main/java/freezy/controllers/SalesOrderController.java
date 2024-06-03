package freezy.controllers;

import freezy.dto.SalesOrderDetailsDTO;
import freezy.dto.SalesOrderStatusDTO;
import freezy.entities.PurchaseOrder;
import freezy.entities.PurchaseOrderStatus;
import freezy.entities.SalesOrder;
import freezy.entities.User;
import freezy.repository.SalesOrderItemsRepository;
import freezy.services.PurchaseOrderService;
import freezy.services.SalesOrderService;
import freezy.services.UserService;
import freezy.utils.Constants;
import freezy.utils.FreazySMSService;
import freezy.utils.UtilsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/salesOrders")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class SalesOrderController {

    @Autowired
    private SalesOrderService salesOrderService;
    @Autowired
    private UserService userService;
    @Autowired
    private PurchaseOrderService purchaseOrderService;

    @Autowired
    private SalesOrderItemsRepository salesOrderItemsRepository;

    @Autowired
    UtilsService utilsService;

    @Autowired
    FreazySMSService freazySMSService;


    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<SalesOrder> getSalesOrders() {
        return salesOrderService.getAllSalesOrders();
    }

    @GetMapping(value= "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public SalesOrder getSalesOrderbyId(@PathVariable String id) {
        return salesOrderService.getSalesOrderById(id);
    }

    @GetMapping(value= "/purchase/{purchaseOrderId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<SalesOrder> getSalesOrdersByPurchaseOrder(@PathVariable String purchaseOrderId) {
        PurchaseOrder purchaseOrder = purchaseOrderService.getPurchaseOrderById(purchaseOrderId);
        return salesOrderService.getAllSalesOrdersByPurchaseOrder(purchaseOrder);
    }

    @GetMapping(value= "/user/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<SalesOrder> getRecentPSalesOrdersByUser(@PathVariable String userId) {
        User user = userService.getUserById(userId);
        return salesOrderService.getRecentSalesOrdersByUser(user);
    }

    @PostMapping(value = "/saveDetails", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object saveSalesOrderDetails(@RequestBody SalesOrderDetailsDTO salesOrderDetailsDTO) {
        log.info(" in post controller");
        //validateStockAndBudgetWithPurchaseOrder
        //validateStockAndBudgetOfSalesOrderWithPO
        PurchaseOrder purchaseOrder = purchaseOrderService.getPurchaseOrderById(salesOrderDetailsDTO.getPoId());
        if(purchaseOrder.getStatus().equalsIgnoreCase(PurchaseOrderStatus.DRAFT.toString())){
            return utilsService.sendResponse(Constants.PO_STATE_NOT_ALLOWED, HttpStatus.OK);
        }
        String doProductsMatch = salesOrderService.validateIncomingProductsWithPO(salesOrderDetailsDTO);
        if(doProductsMatch != null){
            return utilsService.sendResponse(Constants.PO_SO_PRODUCT_ERROR, HttpStatus.OK);
        }
        String isBudgetAndQuantityOnPOValid = salesOrderService.validateIncomingStockWithPurchaseOrder(salesOrderDetailsDTO);
        if(isBudgetAndQuantityOnPOValid != null){
            return utilsService.sendResponse(Constants.PO_BUDGET_STOCK_ERROR, HttpStatus.OK);
        }
        String isValidBudgetAndStockWithPOAndSO = salesOrderService.validateIncomingWithPOAndSO(salesOrderDetailsDTO);
        if(isValidBudgetAndStockWithPOAndSO != null){
            return utilsService.sendResponse(Constants.PO_SO_BUDGET_STOCK_ERROR, HttpStatus.OK);
        }
        SalesOrder salesOrder = salesOrderService.saveSalesOrderDetails(salesOrderDetailsDTO);
        freazySMSService.sendSms(Constants.SEND_SMS2, utilsService.generateSOMessage(salesOrder.getId()));
        return salesOrderService.getSalesOrderById(salesOrder.getId());

    }

    @PostMapping(value = "/changeStatus", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public SalesOrder changeStatus(@RequestBody SalesOrderStatusDTO salesOrderStatusDTO){
        SalesOrder salesOrder = salesOrderService.getSalesOrderById(salesOrderStatusDTO.getId());
        salesOrderService.changeStatus(salesOrder, salesOrderStatusDTO.getOldStatus(), salesOrderStatusDTO.getNewStatus());
        return salesOrder;
    }

    @GetMapping(value= "/statuses/{persona}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map getSalesOrderStatuses(@PathVariable String persona) {
        if(null != persona && persona.equalsIgnoreCase(Constants.CUSTOMER)){
            return Constants.SO_STATUSES_CUSTOMER;
        }
        if(null != persona && persona.equalsIgnoreCase(Constants.VENDOR)){
            return Constants.SO_STATUSES_SUPPLIER;
        }
        return null;
    }
}
