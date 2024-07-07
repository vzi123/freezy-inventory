package freezy.dto.v1;

import freezy.entities.v1.AccessoryV1;
import freezy.entities.v1.ConsignmentV1;
import freezy.entities.v1.ProductV1;
import freezy.entities.v1.ServiceV1;

import java.util.List;

public class ConsignmentDetailsDTOV1 {

    List<ProductV1> products;
    List<AccessoryV1> accessories;
    List<ServiceV1> services;
    ConsignmentV1 consignment;

    public List<ProductV1> getProducts() {
        return products;
    }

    public void setProducts(List<ProductV1> products) {
        this.products = products;
    }

    public List<AccessoryV1> getAccessories() {
        return accessories;
    }

    public void setAccessories(List<AccessoryV1> accessories) {
        this.accessories = accessories;
    }

    public List<ServiceV1> getServices() {
        return services;
    }

    public void setServices(List<ServiceV1> services) {
        this.services = services;
    }

    public ConsignmentV1 getConsignment() {
        return consignment;
    }

    public void setConsignment(ConsignmentV1 consignment) {
        this.consignment = consignment;
    }
}
