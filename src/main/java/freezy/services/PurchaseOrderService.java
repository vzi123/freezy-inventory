package freezy.services;


import freezy.dto.FulfillmentDTO;
import freezy.dto.FulfillmentItemsDTO;
import freezy.dto.POItemsDTO;
import freezy.dto.PurchaseOrderDetailsDTO;
import freezy.entities.*;
import freezy.repository.PurchaseOrderItemsRepository;
import freezy.repository.PurchaseOrderRepository;
import freezy.repository.SalesOrderItemsRepository;
import freezy.utils.Constants;
import freezy.utils.FreazySMSService;
import freezy.utils.UtilsService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.nio.file.LinkOption;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

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

    @Autowired
    PurchaseOrderItemsService purchaseOrderItemsService;

    @Autowired
    FreazySMSService freazySMSService;

    @Autowired
    SalesOrderItemsRepository salesOrderItemsRepository;

    public List<PurchaseOrder> getAllPurchaseOrders() {
        return purchaseOrderRepository.findAll();
    }

    public List<PurchaseOrder> getAllPurchaseOrdersByPersona(String persona) {
        return purchaseOrderRepository.findAllByUserPersona(persona);
    }

    public List<PurchaseOrder> getAllPurchaseOrdersByPersonaAndStates(String persona, List<String> states) {
        return purchaseOrderRepository.findAllByUserPersonaAndStatusIn(persona, states);
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
        PurchaseOrder purchaseOrder = getPurchaseOrder();
        purchaseOrder.setUserPersona(purchaseOrderDetails.getUserPersona());
        purchaseOrder.setUser(userService.getUserById(purchaseOrderDetails.getUserId()));
        purchaseOrder.setDiscount(purchaseOrderDetails.getDiscount());
        purchaseOrder.setComments(purchaseOrderDetails.getComments());
        purchaseOrder.setProject(projectService.getProjectById(purchaseOrderDetails.getProjectId()));
        purchaseOrderRepository.save(purchaseOrder);

        Integer budget = 0;
        List<PurchaseOrderItems> purchaseOrderItems = new ArrayList<PurchaseOrderItems>();
        for (POItemsDTO poDTO: purchaseOrderDetails.getPoItems()
        ) {
            PurchaseOrderItems purchaseOrderItem = getPurchaseOrderItems();
            purchaseOrderItem.setPurchaseOrder(purchaseOrder);
            purchaseOrderItem.setProduct(productService.getProductById(poDTO.getProductId()));
            purchaseOrderItem.setQuantity(poDTO.getQuantity());
            purchaseOrderItem.setPrice(poDTO.getPrice());
            budget = budget + (poDTO.getQuantity() * poDTO.getPrice());
            purchaseOrderItems.add(purchaseOrderItem);
        }
        budget = (int)(budget * (1 - (float)(purchaseOrderDetails.getDiscount())/100));
        purchaseOrderItemsRepository.saveAll(purchaseOrderItems);
        purchaseOrder.setBudget(budget);
        purchaseOrderRepository.saveAndFlush(purchaseOrder);
        return purchaseOrder;
    }

    private PurchaseOrderItems getPurchaseOrderItems() {
        PurchaseOrderItems purchaseOrderItem = new PurchaseOrderItems();
        purchaseOrderItem.setId(utilsService.generateId(Constants.PURCHASE_ORDER_ITEM_PREFIX));
        purchaseOrderItem.setCreatedAt(utilsService.generateDateFormat());
        purchaseOrderItem.setCreatedBy(utilsService.getSuperUser());
        return purchaseOrderItem;
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

    public PurchaseOrder createPOFromQuotation(Quotation quotation){
        try{
            PurchaseOrder purchaseOrder = getPurchaseOrder();
            purchaseOrder.setUserPersona(quotation.getUserPersona());
            purchaseOrder.setUser(quotation.getUser());
            purchaseOrder.setBudget(quotation.getBudget());
            purchaseOrder.setProject(quotation.getProject());
            purchaseOrder.setDiscount(quotation.getDiscount().intValue());
            purchaseOrderRepository.saveAndFlush(purchaseOrder);

            List<PurchaseOrderItems> purchaseOrderItems = new ArrayList<PurchaseOrderItems>();
            for (QuotationItems item: quotation.getQuotationItems()) {
                PurchaseOrderItems purchaseOrderItem = getPurchaseOrderItems();
                purchaseOrderItem.setPurchaseOrder(purchaseOrder);
                purchaseOrderItem.setProduct(item.getProduct());
                purchaseOrderItem.setQuantity(item.getQuantity());
                purchaseOrderItem.setPrice((int)(item.getEffectivePrice() * (float)(1 - (quotation.getDiscount())/100)));
                purchaseOrderItems.add(purchaseOrderItem);
            }
            purchaseOrderItemsService.savePurchaseOrderItems(purchaseOrderItems);
            purchaseOrder.setPurchaseOrderItems(purchaseOrderItems);
            purchaseOrderRepository.saveAndFlush(purchaseOrder);
            //freazySMSService.sendSms(Constants.SEND_SMS, utilsService.generateQuoToPOMessage(quotation.getId(), purchaseOrder.getId()));
            return purchaseOrder;
        }
        catch (Exception e){

        }
        return null;
    }

    private PurchaseOrder getPurchaseOrder() {
        PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrder.setId(utilsService.generateId(Constants.PURCHASE_ORDER_PREFIX));
        purchaseOrder.setCreatedAt(utilsService.generateDateFormat());
        purchaseOrder.setCreatedBy(utilsService.getSuperUser());
        purchaseOrder.setStatus(PurchaseOrderStatus.DRAFT.toString());
        return purchaseOrder;
    }

    public FulfillmentDTO getFulfillmentDetails(PurchaseOrder purchaseOrder){
        FulfillmentDTO dto = new FulfillmentDTO();
        List<FulfillmentItemsDTO> items = new ArrayList<FulfillmentItemsDTO>();
        List<Object> resultSet = purchaseOrderRepository.getGivenStockByPurchaseOrder(purchaseOrder.getId());
        List<Object> resultSet2 = salesOrderItemsRepository.getGivenStockBySalesOrderForPurchaseOrder(purchaseOrder.getId());
        Map<String, Integer> purchaseOrderMap = new HashMap<>();
        Map<String, Integer> salesOrderMap = new HashMap<>();
        for (Object item: resultSet
             ) {
            Object[] itemArr = (Object[])item;
            purchaseOrderMap.put(itemArr[0].toString(), Integer.parseInt(itemArr[1].toString()));
        }

        for (Object item: resultSet2
        ) {
            Object[] itemArr = (Object[])item;
            salesOrderMap.put(itemArr[0].toString(), Integer.parseInt(itemArr[1].toString()));
        }
        for(String key: purchaseOrderMap.keySet()){
            FulfillmentItemsDTO itemDto = new FulfillmentItemsDTO();
            itemDto.setProductId(key);
            itemDto.setProductName(productService.getProductById(key).getName());
            if(salesOrderMap.keySet().contains(key)){
                itemDto.setQuantity(purchaseOrderMap.get(key) - salesOrderMap.get(key));
            }
            else{
                itemDto.setQuantity(purchaseOrderMap.get(key));
            }
            items.add(itemDto);
        }
        dto.setFulfillmentItemsDTOs(items);
        dto.setPoId(purchaseOrder.getId());
        return dto;
    }

}
