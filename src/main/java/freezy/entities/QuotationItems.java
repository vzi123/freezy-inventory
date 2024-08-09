package freezy.entities;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "quotation_items")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuotationItems {

    @Id
    private String id;
    @ManyToOne
    @JoinColumn(name = "quotation_id", nullable = false)
    @JsonIgnore
    @JsonBackReference
    private Quotation quotation;
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
    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private Integer unitPrice;

    @Column(nullable = false)
    private Integer discountAmount;

    @Column(nullable = false)
    private Integer effectivePrice;

    @Column(nullable = false)
    @JoinColumn(name = "created_at")
    private String createdAt;


}
