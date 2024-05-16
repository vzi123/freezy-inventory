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
import freezy.utils.UtilsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/salesOrders")
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
        String isBudgetAndQuantityOnPOValid = salesOrderService.validateIncomingStockWithPurchaseOrder(salesOrderDetailsDTO);
        String isValidBudgetAndStockWithPOAndSO = salesOrderService.validateIncomingWithPOAndSO(salesOrderDetailsDTO);
        if(isBudgetAndQuantityOnPOValid != null){
            return utilsService.sendResponse(Constants.PO_BUDGET_STOCK_ERROR, HttpStatus.OK);
        }
        if(isValidBudgetAndStockWithPOAndSO != null){
            return utilsService.sendResponse(Constants.PO_SO_BUDGET_STOCK_ERROR, HttpStatus.OK);
        }
        PurchaseOrder purchaseOrder = purchaseOrderService.getPurchaseOrderById(salesOrderDetailsDTO.getPoId());
        if(!purchaseOrder.getStatus().equalsIgnoreCase(PurchaseOrderStatus.DRAFT.toString())){
            SalesOrder salesOrder = salesOrderService.saveSalesOrderDetails(salesOrderDetailsDTO);
            return salesOrderService.getSalesOrderById(salesOrder.getId());
        }
        return utilsService.sendResponse("Validation Failed", HttpStatus.OK);
    }

    @PostMapping(value = "/changeStatus", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public SalesOrder changeStatus(@RequestBody SalesOrderStatusDTO salesOrderStatusDTO){
        SalesOrder salesOrder = salesOrderService.getSalesOrderById(salesOrderStatusDTO.getId());
        salesOrderService.changeStatus(salesOrder, salesOrderStatusDTO.getOldStatus(), salesOrderStatusDTO.getNewStatus());
        return salesOrder;
    }
}
