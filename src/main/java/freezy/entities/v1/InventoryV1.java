package freezy.entities.v1;



import com.fasterxml.jackson.annotation.JsonIgnore;
import freezy.entities.v1.ProductV1;
import freezy.entities.v1.UserV1;
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
    private Integer inventory;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductV1 product;
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
