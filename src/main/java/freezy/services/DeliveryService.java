package freezy.services;


import freezy.entities.Delivery;
import freezy.repository.DeliveryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeliveryService {
    @Autowired
    private DeliveryRepository deliveryRepository;

    public List<Delivery> getAllDeliveries() {
        return deliveryRepository.findAll();
    }

    public Delivery getDeliveryById(String id) {
        return deliveryRepository.findById(id).orElse(null);
    }

    public void saveDelivery(Delivery delivery) {
        deliveryRepository.save(delivery);
    }

    public void deleteDelivery(String id) {
        deliveryRepository.deleteById(id);
    }
}

