package freezy.entities;



import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "procurement")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Procurement {
    @Id
    private String id;

    @Column(nullable = false)
    private String date;

    @Column(nullable = false)
    private String description;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private int cost;

    @Column(nullable = false)
    private String vendor;

    // Getters and setters
}
