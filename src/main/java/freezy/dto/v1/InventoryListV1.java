package freezy.dto.v1;

import freezy.entities.v1.AccessoryV1;
import freezy.entities.v1.ProductV1;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

public class InventoryListV1 {

    String id;
    Integer inventory;
    ProductV1 product;
    String uom;
    AccessoryV1 accessory;
    String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public AccessoryV1 getAccessory() {
        return accessory;
    }

    public void setAccessory(AccessoryV1 accessory) {
        this.accessory = accessory;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getInventory() {
        return inventory;
    }

    public void setInventory(Integer inventory) {
        this.inventory = inventory;
    }

    public ProductV1 getProduct() {
        return product;
    }

    public void setProduct(ProductV1 product) {
        this.product = product;
    }

    public String getUom() {
        return uom;
    }

    public void setUom(String uom) {
        this.uom = uom;
    }
}
