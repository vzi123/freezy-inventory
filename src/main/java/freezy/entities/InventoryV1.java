package freezy.entities;



import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "inventory_v1")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InventoryV1 {
    @Id
    private String id;

    @Column(nullable = false)
    private Integer stock;

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
//
//    @ManyToOne
//    @JoinColumn(name = "created_by")
//    private User createdBy;
//
//    @ManyToOne
//    @JoinColumn(name = "updated_by")
//    private User updatedBy;
//
//    @ManyToOne
//    @JoinColumn(name = "deleted_by")
//    private User deletedBy;
//
    @ManyToOne
    @JoinColumn(name = "created_by")
    @JsonIgnore
    private UserV1 createdBy;

    @JoinColumn(name = "created_at")
    @Column(nullable = false)
    @JsonIgnore
    private String createdAt;

    @ManyToOne
    @JoinColumn(name = "updated_by")
    @JsonIgnore
    private UserV1 updatedBy;

    @JoinColumn(name = "udpated_at")
    @Column(nullable = false)
    @JsonIgnore
    private String updatedAt;

}
