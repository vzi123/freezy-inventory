package freezy.services;

import freezy.dto.FulfillmentDTO;
import freezy.entities.PurchaseOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FulfillmentService {

    @Autowired
    PurchaseOrderService purchaseOrderService;

    public List<FulfillmentDTO> getFulfillments(){
        List<FulfillmentDTO> dtos = new ArrayList<FulfillmentDTO>();
        List<PurchaseOrder> purchaseOrders = purchaseOrderService.getAllPurchaseOrders();
        for (PurchaseOrder po:
             purchaseOrders) {
            FulfillmentDTO dto = new FulfillmentDTO();
            dto = purchaseOrderService.getFulfillmentDetails(po);
            dtos.add(dto);
        }
        return dtos;
    }
}
