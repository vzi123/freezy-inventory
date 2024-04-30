package freezy.controllers;

import freezy.entities.Product;
import freezy.entities.PurchaseOrder;
import freezy.services.ProductService;
import freezy.services.PurchaseOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/purchaseOrders")
public class PurchaseOrderController {
    @Autowired
    private PurchaseOrderService purchaseOrderService;


    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<PurchaseOrder> getAllPurchaseOrders() {
        return purchaseOrderService.getAllPurchaseOrders();
    }

    @GetMapping(value= "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public PurchaseOrder getPurchaseOrderById(@PathVariable String id) {
        return purchaseOrderService.getPurchaseOrderById(id);
    }
}
