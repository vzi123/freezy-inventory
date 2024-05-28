package freezy.dto;

import freezy.entities.PurchaseOrder;

public class PurchaseOrderDTO {

    PurchaseOrder purchaseOrder;
    FulfillmentDTO fulfillment;

    public PurchaseOrder getPurchaseOrder() {
        return purchaseOrder;
    }

    public void setPurchaseOrder(PurchaseOrder purchaseOrder) {
        this.purchaseOrder = purchaseOrder;
    }

    public FulfillmentDTO getFulfillment() {
        return fulfillment;
    }

    public void setFulfillment(FulfillmentDTO fulfillment) {
        this.fulfillment = fulfillment;
    }
}
