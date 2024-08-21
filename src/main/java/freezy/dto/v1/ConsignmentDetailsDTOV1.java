package freezy.dto.v1;

import freezy.entities.v1.AccessoryV1;
import freezy.entities.v1.ConsignmentV1;
import freezy.entities.v1.ProductV1;
import freezy.entities.v1.ServiceV1;

import java.util.List;

public class ConsignmentDetailsDTOV1 {

    List<ProductDetailsDTO> products;
    List<AccessoryDetailsDTO> accessories;
    List<ServiceDetailsDTO> services;
    ConsignmentV1 consignment;

    public List<ProductDetailsDTO> getProducts() {
        return products;
    }

    public void setProducts(List<ProductDetailsDTO> products) {
        this.products = products;
    }

    public List<AccessoryDetailsDTO> getAccessories() {
        return accessories;
    }

    public void setAccessories(List<AccessoryDetailsDTO> accessories) {
        this.accessories = accessories;
    }

    public ConsignmentV1 getConsignment() {
        return consignment;
    }

    public void setConsignment(ConsignmentV1 consignment) {
        this.consignment = consignment;
    }

    public List<ServiceDetailsDTO> getServices() {
        return services;
    }

    public void setServices(List<ServiceDetailsDTO> services) {
        this.services = services;
    }
}
