package freezy.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "sales_order_items")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SalesOrderItems {

    @Id
    private String id;

    @ManyToOne
    @JoinColumn(name = "so_id", nullable = false)
    @JsonIgnore
    private SalesOrder salesOrder;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private Integer price;

//    @ManyToOne
//    @JoinColumn(name = "created_by")
//    private User createdBy;
//
//    @Column(nullable = false)
//    private String created_at;
}
