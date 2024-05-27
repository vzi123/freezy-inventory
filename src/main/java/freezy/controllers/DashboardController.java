package freezy.controllers;

import freezy.dto.*;
import freezy.entities.*;
import freezy.repository.StockAlertsRepository;
import freezy.services.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/dashboard")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class DashboardController {

    //get StockAlerts
    //get stock
    //get Inventory Logs
    //get Fulfillment Information
    //get Payables
    //get Receivables

    @Autowired
    StockAlertService stockAlertService;

    @Autowired
    private InventoryLogService inventoryLogService;

    @Autowired
    private InventoryService inventoryService;

    @Autowired
    PayableService payableService;

    @Autowired
    ReceivableService receivableService;

    @Autowired
    FulfillmentService fulfillmentService;

    @Autowired
    QuotationService quotationService;

    @Autowired
    ProcurementService procurementService;

    @GetMapping(value = "/stockAlerts", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<StockAlertDTO> getAllStockAlerts() {
        List<StockAlerts> stockAlerts = stockAlertService.getAllStockAlerts();
        List<Inventory> inventories = inventoryService.getAllInventory();
        List<StockAlertDTO> alertsDTO = new ArrayList<>();
        Map<String, StockAlerts> alertsMap = new HashMap<>();
        Map<String, Inventory> inventoryMap = new HashMap<>();
        for(StockAlerts stockAlert: stockAlerts){
            alertsMap.put(stockAlert.getProduct().getId(), stockAlert);
        }
        for(Inventory inventory: inventories){
            inventoryMap.put(inventory.getProduct().getId(), inventory);
        }
        for(String key: inventoryMap.keySet()){
            if(null != alertsMap.get(key)){
                if(inventoryMap.get(key).getInventory() < alertsMap.get(key).getAlertQuantity()){
                    StockAlertDTO stockAlertDTO = new StockAlertDTO();
                    stockAlertDTO.setProductId(inventoryMap.get(key).getProduct().getId());
                    stockAlertDTO.setProductName(inventoryMap.get(key).getProduct().getName());
                    stockAlertDTO.setAlertQuantity(alertsMap.get(key).getAlertQuantity());
                    stockAlertDTO.setCurrentQuantity(inventoryMap.get(key).getInventory());
                    alertsDTO.add(stockAlertDTO);

                }
            }
        }
        return alertsDTO;
    }

    @GetMapping(value = "/inventoryLog", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<InventoryLogDTO> getInventoryLog() {
        List<InventoryLog> inventoryLogs = inventoryLogService.getAllInventoryLogs();
        List<InventoryLogDTO> logDTOs = new ArrayList<InventoryLogDTO>();
        for(InventoryLog inventoryLog: inventoryLogs){
            InventoryLogDTO dto = new InventoryLogDTO();
            dto.setDescription(inventoryLog.getInventory().getProduct().getDescription());
            dto.setComments(inventoryLog.getComments());
            dto.setInOut(inventoryLog.getInOut().name());
            dto.setCreatedAt(inventoryLog.getCreatedAt());
            dto.setProductId(inventoryLog.getInventory().getProduct().getId());
            dto.setProductName(inventoryLog.getInventory().getProduct().getName());
            logDTOs.add(dto);
        }
        return logDTOs;
    }

    @GetMapping(value = "/stock", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Inventory> getStock() {
        return inventoryService.getAllInventory();
    }

    @GetMapping(value = "/payables", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<PayableDTO> getAllPayables() {
        List<PayableDTO> dtos = new ArrayList<PayableDTO>();
        List<Payable> payables = payableService.getAllPayables();
        for (Payable payable: payables){
            PayableDTO dto = new PayableDTO();
            dto.setAmount(payable.getAmount());
            dto.setId(payable.getId());
            dto.setStatus(payable.getStatus().name());
            dto.setComments(payable.getComments());
            dto.setCustomerId(payable.getVendor().getId());
            dto.setCreatedAt(payable.getCreatedAt());
            dto.setProjectId(payable.getProject().getId());
            dto.setSalesOrderId(payable.getSalesOrder().getId());
            dtos.add(dto);
        }
        return dtos;
    }

    @GetMapping(value = "/receivables", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ReceivableDTO> getAllReceivables() {
        List<ReceivableDTO> dtos = new ArrayList<ReceivableDTO>();
        List<Receivable> receivables = receivableService.getAllReceivables();
        for (Receivable receivable: receivables){
            ReceivableDTO dto = new ReceivableDTO();
            dto.setAmount(receivable.getAmount());
            dto.setId(receivable.getId());
            dto.setStatus(receivable.getStatus().name());
            dto.setComments(receivable.getComments());
            dto.setCustomerId(receivable.getCustomer().getId());
            dto.setCreatedAt(receivable.getCreatedAt());
            dto.setProjectId(receivable.getProject().getId());
            dto.setSalesOrderId(receivable.getSalesOrder().getId());
            dtos.add(dto);
        }
        return dtos;
    }

    @GetMapping(value = "/fulfillment", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<FulfillmentDTO> getAllFulfillments() {
        return fulfillmentService.getFulfillments();
    }

    @GetMapping(value = "/openQuotations", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<QuotationDTO> getOpenQuotations() {
        List<Quotation> quotations = quotationService.getAllQuotations();
        List<QuotationDTO> quotationDTOS = new ArrayList<>();
        for(Quotation quotation: quotations){
            if(!(quotation.getStatus().equalsIgnoreCase(QuotationStatus.CANCELLED.name())
            && (quotation.getStatus().equalsIgnoreCase(QuotationStatus.CONVERTED.name())))){
                QuotationDTO dto = new QuotationDTO();
                dto.setStatus(quotation.getStatus());
                dto.setBudget(quotation.getBudget());
                dto.setQuotationId(quotation.getId());
                dto.setProjectId(quotation.getProject().getId());
                dto.setProjectName(quotation.getProject().getName());
                dto.setUserId(quotation.getUser().getFirst_name() + " " + quotation.getUser().getLast_name());
                quotationDTOS.add(dto);
            }
        }
        return quotationDTOS;
    }

    @GetMapping(value = "/procurements", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ProcurementDTO> getAllProcurements() {
        return procurementService.getAllProcurements();
    }
}
