package freezy.controllers;

import freezy.entities.PurchaseOrder;
import freezy.entities.SalesOrder;
import freezy.entities.User;
import freezy.services.PurchaseOrderService;
import freezy.services.SalesOrderService;
import freezy.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
