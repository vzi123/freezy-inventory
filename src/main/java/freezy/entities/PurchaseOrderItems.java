package freezy.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "purchase_order_items")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseOrderItems {

    @Id
    private String id;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "created_by")
    private User createdBy;

    @Column(nullable = false)
    @JoinColumn(name = "created_at")
    private String createdAt;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private Integer price;

    @ManyToOne
    @JoinColumn(name = "po_id", nullable = false)
    @JsonIgnore
    @JsonBackReference
    private PurchaseOrder purchaseOrder;


    public Product getProduct() {
        return product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public PurchaseOrder getPurchaseOrder() {
        return purchaseOrder;
    }
}
