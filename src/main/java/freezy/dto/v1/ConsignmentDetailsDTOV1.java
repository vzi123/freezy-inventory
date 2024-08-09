package freezy.dto.v1;

import freezy.entities.Accessory;
import freezy.entities.Consignment;
import freezy.entities.Product;
import freezy.entities.Service;

import java.util.List;

public class ConsignmentDetailsDTOV1 {

    List<Product> products;
    List<Accessory> accessories;
    List<Service> services;
    Consignment consignment;

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public List<Accessory> getAccessories() {
        return accessories;
    }

    public void setAccessories(List<Accessory> accessories) {
        this.accessories = accessories;
    }

    public List<Service> getServices() {
        return services;
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }

    public Consignment getConsignment() {
        return consignment;
    }

    public void setConsignment(Consignment consignment) {
        this.consignment = consignment;
    }
}
