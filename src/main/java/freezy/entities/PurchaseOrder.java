package freezy.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
//
//    public User getUser() {
//        return user;
//    }
//
//    public void setUser(User user) {
//        this.user = user;
//    }

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String userPersona;

    @Column(nullable = false)
    private Integer budget;

    @Column(nullable = false)
    private String status;

    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @ManyToOne
    @JoinColumn(name = "created_by")
    private User createdBy;

    @JoinColumn(name = "created_at")
    @Column(nullable = false)
    private String createdAt;

    @OneToMany(mappedBy = "purchaseOrder")
    private List<PurchaseOrderItems> purchaseOrderItems;

    @OneToMany(mappedBy = "purchaseOrder")
    private List<SalesOrder> salesOrders;

}
