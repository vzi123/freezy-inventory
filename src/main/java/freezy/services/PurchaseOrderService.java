package freezy.services;


import freezy.dto.POItemsDTO;
import freezy.dto.PurchaseOrderDetailsDTO;
import freezy.entities.PurchaseOrder;
import freezy.entities.PurchaseOrderItems;
import freezy.entities.PurchaseOrderStatus;
import freezy.entities.User;
import freezy.repository.PurchaseOrderItemsRepository;
import freezy.repository.PurchaseOrderRepository;
import freezy.utils.Constants;
import freezy.utils.UtilsService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.LinkOption;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
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
    private PurchaseOrderItemsRepository purchaseOrderItemsRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ProductService productService;

    @Autowired
    UtilsService utilsService;

    public List<PurchaseOrder> getAllPurchaseOrders() {
        return purchaseOrderRepository.findAll();
    }

    public PurchaseOrder getPurchaseOrderById(String id) {
        return purchaseOrderRepository.findById(id).orElse(null);
    }

    public void savePurchaseOrder(PurchaseOrder purchaseOrder) {
        log.info(" in service");
        if(null == purchaseOrder.getId()){
            purchaseOrder.setId(utilsService.generateId(Constants.PURCHASE_ORDER_PREFIX));
            purchaseOrder.setCreatedAt(utilsService.generateDateFormat());
            purchaseOrder.setCreatedBy(utilsService.getSuperUser());
            purchaseOrder.setStatus(PurchaseOrderStatus.DRAFT.toString());
        }
        purchaseOrderRepository.save(purchaseOrder);
    }

    public PurchaseOrder savePurchaseOrderDetails(PurchaseOrderDetailsDTO purchaseOrderDetails) {
        log.info(" in service");
        PurchaseOrder purchaseOrder = new PurchaseOrder();
        //if(null != purchaseOrder && (purchaseOrder.getStatus().equalsIgnoreCase(PurchaseOrderStatus.APPROVED.toString()) || ){
        purchaseOrder.setId(utilsService.generateId(Constants.PURCHASE_ORDER_PREFIX));
        purchaseOrder.setCreatedAt(utilsService.generateDateFormat());
        purchaseOrder.setCreatedBy(utilsService.getSuperUser());
        purchaseOrder.setUserPersona(purchaseOrderDetails.getUserPersona());
        purchaseOrder.setStatus(PurchaseOrderStatus.DRAFT.toString());
        purchaseOrder.setUser(userService.getUserById(purchaseOrderDetails.getUserId()));
        purchaseOrder.setBudget(purchaseOrderDetails.getBudget());
        purchaseOrder.setProject(projectService.getProjectById(purchaseOrderDetails.getProjectId()));
        purchaseOrderRepository.save(purchaseOrder);

        List<PurchaseOrderItems> purchaseOrderItems = new ArrayList<PurchaseOrderItems>();
        for (POItemsDTO poDTO: purchaseOrderDetails.getPoItems()
        ) {
            PurchaseOrderItems purchaseOrderItem = new PurchaseOrderItems();
            purchaseOrderItem.setId(utilsService.generateId(Constants.PURCHASE_ORDER_ITEM_PREFIX));
            purchaseOrderItem.setCreatedAt(utilsService.generateDateFormat());
            purchaseOrderItem.setCreatedBy(utilsService.getSuperUser());
            purchaseOrderItem.setPurchaseOrder(purchaseOrder);
            purchaseOrderItem.setProduct(productService.getProductById(poDTO.getProductId()));
            purchaseOrderItem.setQuantity(poDTO.getQuantity());
            purchaseOrderItems.add(purchaseOrderItem);
        }
        purchaseOrderItemsRepository.saveAll(purchaseOrderItems);
        return purchaseOrder;
        //}
//        return false;
    }

    public void deletePurchaseOrder(String id) {
        purchaseOrderRepository.deleteById(id);
    }

    public List<PurchaseOrder> getRecentPurchaseOrdersByUser(User user){
        return purchaseOrderRepository.findAllByUserOrderByCreatedAtDesc(user);
    }

    public void changeStatus(PurchaseOrder purchaseOrder, String oldStatus, String newStatus){
        purchaseOrder.setStatus(newStatus);
        purchaseOrderRepository.saveAndFlush(purchaseOrder);
    }

    // Other methods as needed
}
