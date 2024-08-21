package freezy.dto.v1;

import freezy.entities.v1.AccessoryV1;
import freezy.entities.v1.ConsignmentV1;
import freezy.entities.v1.ProductV1;
import freezy.entities.v1.ServiceV1;

import java.util.List;

public class ConsignmentDetailsDTOV1 {

    List<InventoryDTOV1> products;
    List<InventoryDTOV1> accessories;
    List<InventoryDTOV1> services;
    ConsignmentV1 consignment;

    public List<InventoryDTOV1> getProducts() {
        return products;
    }

    public void setProducts(List<InventoryDTOV1> products) {
        this.products = products;
    }

    public List<InventoryDTOV1> getAccessories() {
        return accessories;
    }

    public void setAccessories(List<InventoryDTOV1> accessories) {
        this.accessories = accessories;
    }

    public List<InventoryDTOV1> getServices() {
        return services;
    }

    public void setServices(List<InventoryDTOV1> services) {
        this.services = services;
    }

    public ConsignmentV1 getConsignment() {
        return consignment;
    }

    public void setConsignment(ConsignmentV1 consignment) {
        this.consignment = consignment;
    }
}
