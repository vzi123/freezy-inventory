package freezy.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "purchase_order")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseOrder {


    @Id
    private String id;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private UserRole userRole;

    @Column(nullable = false)
    private Integer budget;

    @Column(nullable = false)
    private PurchaseOrderStatus status;

    @Column(nullable = true)
    private Double discount;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @ManyToOne
    @JoinColumn(name = "created_by")
    private User createdBy;

    @JoinColumn(name = "created_at")
    @Column(nullable = false)
    private String createdAt;

    @Column
    private String comments;

    @OneToMany(mappedBy = "purchaseOrder")
    private List<PurchaseOrderItems> purchaseOrderItems;

    @OneToMany(mappedBy = "purchaseOrder")
    private List<Endorsement> endorsements;

    @OneToMany(mappedBy = "purchaseOrder")
    @JsonManagedReference
    private List<SalesOrder> salesOrders;

}
