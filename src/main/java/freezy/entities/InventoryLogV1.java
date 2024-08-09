package freezy.entities;



import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "inventory_log_v1")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InventoryLogV1 {
    @Id
    private String id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "accessory_id")
    private Accessory accessory;

    @ManyToOne
    @JoinColumn(name = "service_id")
    private Service service;

    @Enumerated(EnumType.STRING)
    @JoinColumn(name = "in_out")
    private InventoryLogEntryV1 inOut;

    @Column(nullable = false)
    private String comments;

    @Column
    private Integer quantity;

    @Column(nullable = false)
    @JoinColumn(name = "created_at")
    private String createdAt;

    @Column(nullable = false)
    private Integer amount;

    @ManyToOne
    @JoinColumn(name = "consignment_id")
    private Consignment consignment;

    @Column
    private String iduSerial;

    @Column
    private String oduSerial;


}
