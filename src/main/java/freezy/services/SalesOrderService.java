package freezy.services;

import freezy.dto.*;
import freezy.entities.*;
import freezy.repository.SalesOrderItemsRepository;
import freezy.repository.SalesOrderRepository;
import freezy.utils.Constants;
import freezy.utils.UtilsService;
import io.swagger.models.auth.In;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@Transactional
public class SalesOrderService {
    @Autowired
    private SalesOrderRepository salesOrderRepository;

    @Autowired
    private SalesOrderItemsRepository salesOrderItemsRepository;

    @Autowired
    UtilsService utilsService;

    @Autowired
    UserService userService;

    @Autowired
    PurchaseOrderService purchaseOrderService;

    @Autowired
    ProductService productService;

    @Autowired
    InventoryService inventoryService;

    @Autowired
    InventoryLogService inventoryLogService;

    public List<SalesOrder> getAllSalesOrders() {
        return salesOrderRepository.findAll();
    }

    public SalesOrder getSalesOrderById(String id) {
        return salesOrderRepository.findById(id).orElse(null);
    }

    public List<SalesOrder> getAllSalesOrdersByPurchaseOrder(PurchaseOrder purchaseOrder){
        return salesOrderRepository.findAllByPurchaseOrder(purchaseOrder);
    }

    public List<SalesOrder> getRecentSalesOrdersByUser(User user){
        return salesOrderRepository.findAllByUserOrderByCreatedAtDesc(user);
    }

    public SalesOrder saveSalesOrderDetails(SalesOrderDetailsDTO salesOrderDetailsDTO) {
        log.info(" in service");
        SalesOrder salesOrder = new SalesOrder();
        salesOrder.setId(utilsService.generateId(Constants.SALES_ORDER_PREFIX));
        salesOrder.setCreatedAt(utilsService.generateDateFormat());
        salesOrder.setCreatedBy(utilsService.getSuperUser());
        salesOrder.setStatus(SalesOrderStatus.RAISED.toString());
        salesOrder.setUserPersona(salesOrderDetailsDTO.getUserPersona());
        salesOrder.setUser(userService.getUserById(salesOrderDetailsDTO.getUserId()));
        salesOrder.setPurchaseOrder(purchaseOrderService.getPurchaseOrderById(salesOrderDetailsDTO.getPoId()));
        salesOrderRepository.saveAndFlush(salesOrder);

        List<SalesOrderItems> salesOrderItems = new ArrayList<SalesOrderItems>();
        for (SOItemsDTO soItems: salesOrderDetailsDTO.getSoItems()
        ) {
            SalesOrderItems salesOrderItem = new SalesOrderItems();
            salesOrderItem.setId(utilsService.generateId(Constants.SALES_ORDER_ITEM_PREFIX));
            salesOrderItem.setCreatedAt(utilsService.generateDateFormat());
            salesOrderItem.setCreatedBy(utilsService.getSuperUser());
            salesOrderItem.setSalesOrder(salesOrder);
            salesOrderItem.setProduct(productService.getProductById(soItems.getProductId()));
            salesOrderItem.setQuantity(soItems.getQuantity());
            InventoryDTO inventoryDTO = new InventoryDTO();
            inventoryDTO.setStock(soItems.getQuantity());
            inventoryDTO.setProductId(soItems.getProductId());
            inventoryService.incrementOrDecrementInventory(inventoryDTO, Constants.INVENTORY_DEDUCT, salesOrderItem.getId());
            salesOrderItem.setPrice(soItems.getPrice());
            salesOrderItems.add(salesOrderItem);
        }
        salesOrderItemsRepository.saveAllAndFlush(salesOrderItems);
        return salesOrder;
    }

    public Boolean validateBudgetAndStock(SalesOrderDetailsDTO salesOrderDetails, Integer budget){
        try{
            List budgetAndStock = salesOrderItemsRepository.getStockAndPriceByPurchaseOrder(salesOrderDetails.getPoId());
            Map<String, CompareDTO> existingDetails = new HashMap<String, CompareDTO>();
            Map<String, CompareDTO> newDetails = new HashMap<String, CompareDTO>();
            Map<String, Integer> inventoryMap = new HashMap<String, Integer>();
            for (Object obj: budgetAndStock) {
                CompareDTO compareDTO = new CompareDTO();
                Object[] objects = (Object[]) obj;
                compareDTO.setPrice(Integer.parseInt(objects[0].toString()));
                compareDTO.setQuantity(Integer.parseInt(objects[1].toString()));
                compareDTO.setProductId(objects[2].toString());
                existingDetails.put((objects[2].toString()),compareDTO);
            }
            List<SOItemsDTO> soItems = salesOrderDetails.getSoItems();
            for(SOItemsDTO soItem: soItems){
                CompareDTO compareDTO = new CompareDTO();
                compareDTO.setProductId(soItem.getProductId());
                compareDTO.setQuantity(soItem.getQuantity());
                compareDTO.setPrice(soItem.getPrice());
                newDetails.put(soItem.getProductId(), compareDTO);
            }
            Map<String, Integer> priceMap = new HashMap<String, Integer>();
            Map<String, Integer> stockMap = new HashMap<String, Integer>();
            Integer compareBudget = 0;
            for(String key: newDetails.keySet()){
                Integer totalStock = ((CompareDTO)newDetails.get(key)).getQuantity() + ((CompareDTO)existingDetails.get(key)).getQuantity();
                Integer totalBudget = (((CompareDTO)newDetails.get(key)).getQuantity() * ((CompareDTO)newDetails.get(key)).getPrice()) +
                        ((((CompareDTO)existingDetails.get(key)).getQuantity() * ((CompareDTO)existingDetails.get(key)).getPrice()));
                priceMap.put(key, totalBudget);
                compareBudget = compareBudget + totalBudget;
                stockMap.put(key, totalStock);
                inventoryMap.put(key, inventoryService.getInventoryById(key).getInventory());
            }
            log.info(" the map " + stockMap + " " + priceMap + " " + budget + " " + compareBudget);
            if(budget >= compareBudget){
                if(null != inventoryMap && null != stockMap){
                    for(String product: stockMap.keySet()){
                        if(stockMap.get(product) >= inventoryMap.get(product)) return false;
                    }
                    return true;
                }
            }
            return false;

        }
        catch (Exception e){

        }
        return true;
    }

    public void changeStatus(SalesOrder salesOrder, String oldStatus, String newStatus){
        salesOrder.setStatus(newStatus);
        if(newStatus.equalsIgnoreCase(SalesOrderStatus.DELIVERED.toString())){
            PurchaseOrder purchaseOrder = salesOrder.getPurchaseOrder();
            purchaseOrder.setStatus(PurchaseOrderStatus.PARTIALLY_DELIVERED.toString());
            purchaseOrderService.savePurchaseOrder(purchaseOrder);
        }
        if(newStatus.equalsIgnoreCase(SalesOrderStatus.CLOSED.toString())){
            PurchaseOrder purchaseOrder = salesOrder.getPurchaseOrder();
            purchaseOrder.setStatus(PurchaseOrderStatus.PARTIAL_PAYMENT_RECEIVED.toString());
            purchaseOrderService.savePurchaseOrder(purchaseOrder);
        }
        salesOrderRepository.saveAndFlush(salesOrder);
    }
}
