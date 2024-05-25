package freezy.controllers;

import freezy.entities.PurchaseOrder;
import freezy.entities.PurchaseOrderItems;
import freezy.services.PurchaseOrderItemsService;
import freezy.services.PurchaseOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/purchaseOrderItems")
public class PurchaseOrderItemsController {

    @Autowired
    private PurchaseOrderItemsService purchaseOrderItemsService;

    @Autowired
    private PurchaseOrderService purchaseOrderService;

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<PurchaseOrderItems> getAllPurchaseOrders() {
        return purchaseOrderItemsService.getPurchaseOrderItems();
    }

    @GetMapping(value= "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public PurchaseOrderItems getPurchaseOrderItemsById(@PathVariable String id) {
        return purchaseOrderItemsService.getPurchaseOrderItemById(id);
    }

    @GetMapping(value= "/po/{poId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<PurchaseOrderItems> getPurchaseOrderItemsByPurchaseOrderId(@PathVariable String poId) {
        return purchaseOrderItemsService.getPurchaseOrderItemsByPurchaseOrder(purchaseOrderService.getPurchaseOrderById(poId));
    }


}
