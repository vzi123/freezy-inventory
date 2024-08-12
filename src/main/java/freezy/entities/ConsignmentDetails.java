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
    private Double subTotal;
    @Column
    private Double taxAmount;
    @Column
    private String taxPercentage;
    @Column
    private Double totalAmount;
    @Column
    private ConsignmentDirection direction;
    @Column
    private String comments;

    @Column
    private String iduSerialNo;

    @Column
    private String oduSerialNo;

    @Column(nullable = false)
    @JoinColumn(name = "created_at")
    private String createdAt;
}
