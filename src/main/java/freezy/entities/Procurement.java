package freezy.entities;



import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "procurement")
public class Procurement {
    @Id
    private String id;

    @Column(nullable = false)
    private LocalDateTime date;

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
