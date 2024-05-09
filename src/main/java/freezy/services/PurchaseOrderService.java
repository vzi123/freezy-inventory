package freezy.services;


import freezy.entities.PurchaseOrder;
import freezy.entities.User;
import freezy.repository.PurchaseOrderRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.LinkOption;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@Transactional
public class PurchaseOrderService {
    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ProjectService projectService;

    public List<PurchaseOrder> getAllPurchaseOrders() {
        return purchaseOrderRepository.findAll();
    }

    public PurchaseOrder getPurchaseOrderById(String id) {
        return purchaseOrderRepository.findById(id).orElse(null);
    }

    public void savePurchaseOrder(PurchaseOrder purchaseOrder) {
        log.info(" in service");
//        purchaseOrder.setId("PO"+((long) Math.floor(Math.random() * 9_000_000_000L) + 1_000_000_000L));
        purchaseOrder.setCreatedAt(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Timestamp(System.currentTimeMillis())));
        purchaseOrder.setCreatedBy(userService.getUserById("U0001"));
        purchaseOrderRepository.save(purchaseOrder);
    }

    public void deletePurchaseOrder(String id) {
        purchaseOrderRepository.deleteById(id);
    }

    public List<PurchaseOrder> getRecentPurchaseOrdersByUser(User user){
        return purchaseOrderRepository.findAllByUserOrderByCreatedAtDesc(user);
    }

    // Other methods as needed
}
