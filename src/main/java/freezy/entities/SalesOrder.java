package freezy.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "sales_order")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SalesOrder {

    @Id
    private String id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User userId;

    @Column(nullable = false)
    private String userPersona;

    @Column(nullable = false)
    private String status;

    @ManyToOne
    @JoinColumn(name = "po_id", nullable = false)
    @JsonIgnore
    private PurchaseOrder purchaseOrder;

    @OneToMany(mappedBy = "salesOrder")
    private List<SalesOrderItems> salesOrderItems;

//    @ManyToOne
//    @JoinColumn(name = "created_by")
//    private User createdBy;
//
//    @Column(nullable = false)
//    private String created_at;
}
