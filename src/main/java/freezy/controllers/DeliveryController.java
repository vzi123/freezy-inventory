package freezy.controllers;


import freezy.entities.Delivery;
import freezy.services.DeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/deliveries")
public class DeliveryController {
    @Autowired
    private DeliveryService deliveryService;

    @GetMapping
    public List<Delivery> getAllDeliveries() {
        return deliveryService.getAllDeliveries();
    }

    @GetMapping("/{id}")
    public Delivery getDeliveryById(@PathVariable String id) {
        return deliveryService.getDeliveryById(id);
    }

    @PostMapping
    public void addDelivery(@RequestBody Delivery delivery) {
        deliveryService.saveDelivery(delivery);
    }

    @PutMapping("/{id}")
    public void updateDelivery(@PathVariable String id, @RequestBody Delivery delivery) {
        if (deliveryService.getDeliveryById(id) != null) {
            deliveryService.saveDelivery(delivery);
        }
    }

    @DeleteMapping("/{id}")
    public void deleteDelivery(@PathVariable String id) {
        deliveryService.deleteDelivery(id);
    }
}
