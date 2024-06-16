package freezy.services;

import freezy.dto.CompareDTO;
import freezy.dto.InventoryDTO;
import freezy.dto.SOItemsDTO;
import freezy.dto.SalesOrderDetailsDTO;
import freezy.entities.*;
import freezy.repository.PayableRepository;
import freezy.repository.ReceivableRepository;
import freezy.repository.SalesOrderItemsRepository;
import freezy.repository.SalesOrderRepository;
import freezy.utils.Constants;
import freezy.utils.FreazySMSService;
import freezy.utils.UtilsService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;

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

    @Autowired
    PayableRepository payableRepository;

    @Autowired
    ReceivableRepository receivableRepository;

    @Autowired
    PayableService payableService;

    @Autowired
    ReceivableService receivableService;

    @Autowired
    FreazySMSService freazySMSService;

    @Autowired
    PurchaseOrderItemsService purchaseOrderItemsService;

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
        PurchaseOrder purchaseOrder =  purchaseOrderService.getPurchaseOrderById(salesOrderDetailsDTO.getPoId());
        getDefaultSO(salesOrder);
        salesOrder.setPurchaseOrder(purchaseOrder);
        salesOrder.setUserPersona(purchaseOrder.getUserPersona());
        salesOrder.setUser(purchaseOrder.getUser());
        salesOrder.setStatus(SalesOrderStatus.DEFAULT.toString());
        salesOrderRepository.saveAndFlush(salesOrder);

        List<SalesOrderItems> salesOrderItems = new ArrayList<SalesOrderItems>();
        for (SOItemsDTO soItems: salesOrderDetailsDTO.getSoItems()
        ) {
            SalesOrderItems salesOrderItem = new SalesOrderItems();
            Product product = productService.getProductById(soItems.getProductId());
            getDefaultSOI(salesOrderItem);
            salesOrderItem.setSalesOrder(salesOrder);
            salesOrderItem.setProduct(product);
            salesOrderItem.setQuantity(soItems.getQuantity());
            createInventoryLogEntry(purchaseOrder, salesOrderItem);
            PurchaseOrderItems poItem = purchaseOrderItemsService.getByPurchaseOrderAndProduct(purchaseOrder, product);
            salesOrderItem.setPrice(poItem.getPrice());
            salesOrderItems.add(salesOrderItem);
        }
        salesOrderItemsRepository.saveAllAndFlush(salesOrderItems);
        createCashEntries(salesOrder);
        salesOrderRepository.saveAndFlush(salesOrder);
        return salesOrder;
    }

    private void createCashEntries(SalesOrder salesOrder) {
        if(salesOrder.getUserPersona().equalsIgnoreCase(Constants.CUSTOMER)){
            salesOrder.setStatus(SalesOrderStatus.RAISED.toString());
            saveReceivable(salesOrder, ReceivableStatus.TO_BE_RECEIVED, "Raised a Receivable");
        }
        if(salesOrder.getUserPersona().equalsIgnoreCase(Constants.VENDOR)){
            salesOrder.setStatus(SalesOrderStatus.STOCK_TO_BE_RECEIVED.name());
            savePayable(salesOrder, PayableStatus.TO_BE_PAID, "Raised a Payable");
        }
    }

    private void createInventoryLogEntry(PurchaseOrder purchaseOrder, SalesOrderItems salesOrderItem) {
        InventoryDTO inventoryDTO = new InventoryDTO();
        inventoryDTO.setStock(salesOrderItem.getQuantity());
        inventoryDTO.setProductId(salesOrderItem.getProduct().getId());
        if(purchaseOrder.getUserPersona().equalsIgnoreCase(Constants.CUSTOMER)){
            inventoryService.incrementOrDecrementInventory(inventoryDTO, Constants.INVENTORY_DEDUCT, salesOrderItem.getId());
        }
        if(purchaseOrder.getUserPersona().equalsIgnoreCase(Constants.VENDOR)){
            inventoryService.incrementOrDecrementInventory(inventoryDTO, Constants.INVENTORY_INC, salesOrderItem.getId());
        }
    }

    private void getDefaultSO(SalesOrder salesOrder) {
        salesOrder.setId(utilsService.generateId(Constants.SALES_ORDER_PREFIX));
        salesOrder.setCreatedAt(utilsService.generateDateFormat());
        salesOrder.setCreatedBy(utilsService.getSuperUser());
    }

    private void getDefaultSOI(SalesOrderItems salesOrderItem) {
        salesOrderItem.setId(utilsService.generateId(Constants.SALES_ORDER_ITEM_PREFIX));
        salesOrderItem.setCreatedAt(utilsService.generateDateFormat());
        salesOrderItem.setCreatedBy(utilsService.getSuperUser());
    }

    public Map<String, CompareDTO> getBudgetAndStock(SalesOrder salesOrder){
        Map<String, CompareDTO> details = new HashMap<String, CompareDTO>();
        List budgetAndStock = salesOrderItemsRepository.getStockAndPriceBySalesOrder(salesOrder.getId());
        for (Object obj: budgetAndStock) {
            CompareDTO compareDTO = new CompareDTO();
            Object[] objects = (Object[]) obj;
            compareDTO.setPrice(Integer.parseInt(objects[0].toString()));
            compareDTO.setQuantity(Integer.parseInt(objects[1].toString()));
            compareDTO.setProductId(objects[2].toString());
            details.put((objects[2].toString()),compareDTO);
        }
        return details;
    }

    public void changeStatus(SalesOrder salesOrder, String oldStatus, String newStatus){
        salesOrder.setStatus(newStatus);
        PurchaseOrder purchaseOrder = salesOrder.getPurchaseOrder();
        if(newStatus.equalsIgnoreCase(SalesOrderStatus.STOCK_DELIVERED.toString())){
            purchaseOrder.setStatus(PurchaseOrderStatus.PARTIALLY_DELIVERED.toString());
            purchaseOrderService.savePurchaseOrder(purchaseOrder);
            saveReceivable(salesOrder, ReceivableStatus.FULL_PAYMENT_RECEIVED, "Payment Received");
        }
        if(newStatus.equalsIgnoreCase(SalesOrderStatus.STOCK_RECEIVED.toString())){
            purchaseOrder.setStatus(PurchaseOrderStatus.PARTIALLY_RECEIVED.toString());
            purchaseOrderService.savePurchaseOrder(purchaseOrder);
            savePayable(salesOrder, PayableStatus.FULL_PAYMENT_DONE, "Payment Done");
        }
        if(newStatus.equalsIgnoreCase(SalesOrderStatus.CLOSED.toString())){
            if(purchaseOrder.getUserPersona().equalsIgnoreCase(Constants.CUSTOMER)) {
                purchaseOrder.setStatus(PurchaseOrderStatus.PARTIAL_PAYMENT_RECEIVED.toString());
            }
            if(purchaseOrder.getUserPersona().equalsIgnoreCase(Constants.VENDOR)) {
                purchaseOrder.setStatus(PurchaseOrderStatus.PARTIAL_PAYMENT_DONE.toString());
            }
            purchaseOrderService.savePurchaseOrder(purchaseOrder);
        }
        salesOrderRepository.saveAndFlush(salesOrder);
    }

    public Payable savePayable(SalesOrder salesOrder, PayableStatus status, String comments){
        PurchaseOrder purchaseOrder = salesOrder.getPurchaseOrder();
        Map<String, CompareDTO> details = getBudgetAndStock(salesOrder);
        Integer payableAmount = 0;
        for(String key: details.keySet()){
            CompareDTO detailsDTO = (CompareDTO) details.get(key);
            payableAmount = payableAmount + (detailsDTO.getQuantity() * detailsDTO.getPrice());
        }

        Payable payable = payableRepository.findBySalesOrder(salesOrder);
        if(null == payable) {
            payable = new Payable();
            payable.setId(utilsService.generateId(Constants.PAYABLE_PREFIX));
            payable.setSalesOrder(salesOrder);
            payable.setProject(purchaseOrder.getProject());
            payable.setVendor(purchaseOrder.getUser());
        }
        payable.setAmount(payableAmount);
        payable.setStatus(status);
        payable.setComments(comments);
        payable.setCreatedAt(utilsService.generateDateFormat());
        payable.setCreatedBy(utilsService.getSuperUser());
        payableRepository.saveAndFlush(payable);
        freazySMSService.sendSms(Constants.SEND_SMS2, utilsService.generatePayableMessage(payableAmount.toString()));
        return payable;
    }

    public Receivable saveReceivable(SalesOrder salesOrder, ReceivableStatus status, String comments){
        PurchaseOrder purchaseOrder = salesOrder.getPurchaseOrder();
        Map<String, CompareDTO> details = getBudgetAndStock(salesOrder);
        Integer receivableAmount = 0;
        for(String key: details.keySet()){
            CompareDTO detailsDTO = (CompareDTO) details.get(key);
            receivableAmount = receivableAmount + (detailsDTO.getQuantity() * detailsDTO.getPrice());
        }

        Receivable receivable = receivableRepository.findBySalesOrder(salesOrder);
        if(null == receivable) {
            receivable = new Receivable();
            receivable.setId(utilsService.generateId(Constants.RECEIVABLE_PREFIX));
            receivable.setSalesOrder(salesOrder);
            receivable.setProject(purchaseOrder.getProject());
            receivable.setCustomer(purchaseOrder.getUser());
        }
        receivable.setAmount(receivableAmount);
        receivable.setCreatedAt(utilsService.generateDateFormat());
        receivable.setCreatedBy(utilsService.getSuperUser());
        receivable.setStatus(status);
        receivable.setComments(comments);
        receivableRepository.saveAndFlush(receivable);
        freazySMSService.sendSms(Constants.SEND_SMS2, utilsService.generateReceivableMessage(receivableAmount.toString()));
        return receivable;
    }

    public Map getBudgetAndStock(PurchaseOrder purchaseOrder){
        Map<String, CompareDTO> details = new HashMap<String, CompareDTO>();
        CompareDTO compareDTO = new CompareDTO();
        List<PurchaseOrderItems> purchaseOrderItems = purchaseOrder.getPurchaseOrderItems();
        for(PurchaseOrderItems purchaseOrderItem: purchaseOrderItems){
            compareDTO.setProductId(purchaseOrderItem.getProduct().getId());
            compareDTO.setQuantity(purchaseOrderItem.getQuantity());
            compareDTO.setPrice(purchaseOrder.getBudget());
            details.put(purchaseOrderItem.getProduct().getId(), compareDTO);
        }
        return details;
    }

    public String validateIncomingStockWithPurchaseOrder(SalesOrderDetailsDTO salesOrderDetailsDTO){
        PurchaseOrder purchaseOrder = purchaseOrderService.getPurchaseOrderById(salesOrderDetailsDTO.getPoId());
        List<PurchaseOrderItems> purchaseOrderItems = purchaseOrder.getPurchaseOrderItems();
        Integer poQuantity = 0;
        Integer inputQuantity = 0;
        Map<String, Integer> poQuantityMap = new HashMap<>();
        for(PurchaseOrderItems item: purchaseOrderItems){
            poQuantityMap.put(item.getProduct().getId(), item.getQuantity());
        }
        List<SOItemsDTO> soItemsDTOS = salesOrderDetailsDTO.getSoItems();
        for (SOItemsDTO dto: soItemsDTOS){
            inputQuantity = dto.getQuantity();
            if(inputQuantity > poQuantityMap.get(dto.getProductId())) return Constants.PO_BUDGET_STOCK_ERROR;
        }
        return null;
    }

    public String validateIncomingWithPOAndSO(SalesOrderDetailsDTO salesOrderDetailsDTO){
        PurchaseOrder purchaseOrder = purchaseOrderService.getPurchaseOrderById(salesOrderDetailsDTO.getPoId());
        List<PurchaseOrderItems> purchaseOrderItems = purchaseOrder.getPurchaseOrderItems();
        List<SalesOrder> salesOrders = purchaseOrder.getSalesOrders();

        Integer inputQuantity = 0;
        Map<String, Integer> poQuantityMap = new HashMap<>();
        Map<String, Integer> soQuantityMap = new HashMap<>();
        for(PurchaseOrderItems pItem: purchaseOrderItems){
            poQuantityMap.put(pItem.getProduct().getId(), pItem.getQuantity());
        }
        for(SalesOrder salesOrder: salesOrders){
            List<SalesOrderItems> items = salesOrder.getSalesOrderItems();
            for(SalesOrderItems sItem: items){
                if(soQuantityMap.keySet().contains(sItem.getProduct().getId())){
                    soQuantityMap.put(sItem.getProduct().getId(), soQuantityMap.get(sItem.getProduct().getId()) + sItem.getQuantity());
                }
                else{
                    soQuantityMap.put(sItem.getProduct().getId(),sItem.getQuantity());
                }
            }
        }
        List<SOItemsDTO> soItemsDTOS = salesOrderDetailsDTO.getSoItems();
        for (SOItemsDTO dto: soItemsDTOS){
            inputQuantity = (null != soQuantityMap.get(dto.getProductId())) ? soQuantityMap.get(dto.getProductId()) + dto.getQuantity() : dto.getQuantity();
            if(null != soQuantityMap.get(dto.getProductId())){
                soQuantityMap.put(dto.getProductId(), soQuantityMap.get(dto.getProductId()) + dto.getQuantity());
            }
            else{
                soQuantityMap.put(dto.getProductId(), dto.getQuantity());
            }
            if(inputQuantity > poQuantityMap.get(dto.getProductId())) return Constants.PO_SO_BUDGET_STOCK_ERROR;
        }
        return null;
    }

    public String validateIncomingProductsWithPO(SalesOrderDetailsDTO salesOrderDetailsDTO){
        PurchaseOrder purchaseOrder = purchaseOrderService.getPurchaseOrderById(salesOrderDetailsDTO.getPoId());
        Set products = new HashSet();
        for(PurchaseOrderItems item: purchaseOrder.getPurchaseOrderItems()){
            products.add(item.getProduct().getId());
        }
        Set incomingProducts = new HashSet();
        for(SOItemsDTO dto : salesOrderDetailsDTO.getSoItems()){
            incomingProducts.add(dto.getProductId());
        }
        if(!products.containsAll(incomingProducts)) return Constants.PO_SO_PRODUCT_ERROR;
        return null;
    }

    public void clonePOtoSO(PurchaseOrder purchaseOrder) {
        SalesOrder salesOrder;
        if(null != purchaseOrder){
            salesOrder = new SalesOrder();
            getDefaultSO(salesOrder);
            salesOrder.setPurchaseOrder(purchaseOrder);
            salesOrder.setStatus(SalesOrderStatus.STOCK_TO_BE_DELIVERED.name());
            salesOrder.setUser(purchaseOrder.getUser());
            salesOrder.setUserPersona(purchaseOrder.getUserPersona());
            salesOrderRepository.saveAndFlush(salesOrder);
            List<SalesOrderItems> salesOrderItems = new ArrayList<>();
            for(PurchaseOrderItems purchaseItem: purchaseOrder.getPurchaseOrderItems()){
                SalesOrderItems salesOrderItem = new SalesOrderItems();
                getDefaultSOI(salesOrderItem);
                salesOrderItem.setSalesOrder(salesOrder);
                clonePOItoSOI(purchaseItem, salesOrderItem);
                salesOrderItems.add(salesOrderItem);
                createInventoryLogEntry(purchaseOrder, salesOrderItem);
            }
            salesOrderItemsRepository.saveAllAndFlush(salesOrderItems);
            salesOrder.setSalesOrderItems(salesOrderItems);
            salesOrderRepository.saveAndFlush(salesOrder);
            createCashEntries(salesOrder);
        }
    }

    public void clonePOItoSOI(PurchaseOrderItems poItem, SalesOrderItems soItem){
        soItem.setQuantity(poItem.getQuantity());
        soItem.setPrice(poItem.getPrice());
        soItem.setProduct(poItem.getProduct());
        soItem.setCreatedAt(poItem.getCreatedAt());
        soItem.setCreatedBy(poItem.getCreatedBy());
    }
}
