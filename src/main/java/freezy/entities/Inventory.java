package freezy.entities;



import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "inventory")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Inventory {
    @Id
    private String id;

    @Column(nullable = false)
    private Integer inventory;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
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
    private User createdBy;

    @JoinColumn(name = "created_at")
    @Column(nullable = false)
    @JsonIgnore
    private String createdAt;

    @ManyToOne
    @JoinColumn(name = "updated_by")
    @JsonIgnore
    private User updatedBy;

    @JoinColumn(name = "udpated_at")
    @Column(nullable = false)
    @JsonIgnore
    private String updatedAt;

}
