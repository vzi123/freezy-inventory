package freezy.entities;


import freezy.entities.v1.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** @noinspection ALL*/
@Entity
@Table(name = "consignment_details")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConsignmentDetails {

    @Id
    private String id;
    @ManyToOne
    @JoinColumn(name = "consignment_id")
    private ConsignmentV1 consignment;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductV1 product;
    @ManyToOne
    @JoinColumn(name = "accessory_id")
    private AccessoryV1 accessory;
    @ManyToOne
    @JoinColumn(name = "service_id")
    private ServiceV1 service;
    @Column
    private InventoryTypeV1 type;
    @Column
    private Integer count;
    @Column
    private Double amount;
    @Column
    private Double tax;
    @Column
    private ConsignmentDirection direction;
    @Column
    private String comments;

    @Column(nullable = false)
    @JoinColumn(name = "created_at")
    private String createdAt;

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ConsignmentV1 getConsignment() {
        return consignment;
    }

    public void setConsignment(ConsignmentV1 consignment) {
        this.consignment = consignment;
    }

    public ProductV1 getProduct() {
        return product;
    }

    public void setProduct(ProductV1 product) {
        this.product = product;
    }

    public AccessoryV1 getAccessory() {
        return accessory;
    }

    public void setAccessory(AccessoryV1 accessory) {
        this.accessory = accessory;
    }

    public ServiceV1 getService() {
        return service;
    }

    public void setService(ServiceV1 service) {
        this.service = service;
    }

    public InventoryTypeV1 getType() {
        return type;
    }

    public void setType(InventoryTypeV1 type) {
        this.type = type;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getTax() {
        return tax;
    }

    public void setTax(Double tax) {
        this.tax = tax;
    }

    public ConsignmentDirection getDirection() {
        return direction;
    }

    public void setDirection(ConsignmentDirection direction) {
        this.direction = direction;
    }
}
