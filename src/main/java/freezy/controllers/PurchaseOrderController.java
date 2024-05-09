package freezy.controllers;

import freezy.entities.Category;
import freezy.entities.Product;
import freezy.entities.PurchaseOrder;
import freezy.entities.User;
import freezy.services.ProductService;
import freezy.services.PurchaseOrderService;
import freezy.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/purchaseOrders")
public class PurchaseOrderController {
    @Autowired
    private PurchaseOrderService purchaseOrderService;

    @Autowired
    private UserService userService;


    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<PurchaseOrder> getAllPurchaseOrders() {
        return purchaseOrderService.getAllPurchaseOrders();
    }

    @GetMapping(value= "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public PurchaseOrder getPurchaseOrderById(@PathVariable String id) {
        return purchaseOrderService.getPurchaseOrderById(id);
    }

    @PostMapping(value = "/save", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public PurchaseOrder addPurchaseOrder(@RequestBody PurchaseOrder purchaseOrder, HttpServletRequest request) {
        log.info(" in post controller");
        purchaseOrderService.savePurchaseOrder(purchaseOrder);

        return purchaseOrderService.getPurchaseOrderById(purchaseOrder.getId());
    }

    @GetMapping(value= "/user/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<PurchaseOrder> getRecentPurchaseOrdersByUser(@PathVariable String userId) {
        User user = userService.getUserById(userId);
        return purchaseOrderService.getRecentPurchaseOrdersByUser(user);
    }

}
