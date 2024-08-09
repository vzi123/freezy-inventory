package freezy.entities;


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
    private Consignment consignment;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
    @ManyToOne
    @JoinColumn(name = "accessory_id")
    private Accessory accessory;
    @ManyToOne
    @JoinColumn(name = "service_id")
    private Service service;
    @Column
    private InventoryType type;
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

    public Consignment getConsignment() {
        return consignment;
    }

    public void setConsignment(Consignment consignment) {
        this.consignment = consignment;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Accessory getAccessory() {
        return accessory;
    }

    public void setAccessory(Accessory accessory) {
        this.accessory = accessory;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public InventoryType getType() {
        return type;
    }

    public void setType(InventoryType type) {
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
