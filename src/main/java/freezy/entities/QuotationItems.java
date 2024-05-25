package freezy.entities;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    @JoinColumn(name = "quo_id", nullable = false)
    @JsonIgnore
    @JsonBackReference
    private Quotation quotation;
}
