package freezy.dto;

import freezy.entities.Inventory;

import java.util.List;

public class DashboardDTO {

    List<StockAlertDTO> stockAlerts;
    List<InventoryLogDTO> inventoryLog;
    List<Inventory> stock;
    List<PayableDTO> payables;
    List<ReceivableDTO> receivables;
    List<FulfillmentDTO> fulfillments;
    List<QuotationDTO> openQuotations;
    List<ProcurementDTO> procurements;


    public List<StockAlertDTO> getStockAlerts() {
        return stockAlerts;
    }

    public void setStockAlerts(List<StockAlertDTO> stockAlerts) {
        this.stockAlerts = stockAlerts;
    }

    public List<InventoryLogDTO> getInventoryLog() {
        return inventoryLog;
    }

    public void setInventoryLog(List<InventoryLogDTO> inventoryLog) {
        this.inventoryLog = inventoryLog;
    }

    public List<Inventory> getStock() {
        return stock;
    }

    public void setStock(List<Inventory> stock) {
        this.stock = stock;
    }

    public List<PayableDTO> getPayables() {
        return payables;
    }

    public void setPayables(List<PayableDTO> payables) {
        this.payables = payables;
    }

    public List<ReceivableDTO> getReceivables() {
        return receivables;
    }

    public void setReceivables(List<ReceivableDTO> receivables) {
        this.receivables = receivables;
    }

    public List<FulfillmentDTO> getFulfillments() {
        return fulfillments;
    }

    public void setFulfillments(List<FulfillmentDTO> fulfillments) {
        this.fulfillments = fulfillments;
    }

    public List<QuotationDTO> getOpenQuotations() {
        return openQuotations;
    }

    public void setOpenQuotations(List<QuotationDTO> openQuotations) {
        this.openQuotations = openQuotations;
    }

    public List<ProcurementDTO> getProcurements() {
        return procurements;
    }

    public void setProcurements(List<ProcurementDTO> procurements) {
        this.procurements = procurements;
    }
}
