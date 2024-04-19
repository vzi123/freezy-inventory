package freezy.entities;




import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "delivery")
@Table(name = "delivery")
public class Delivery {
    @Id
    private String id;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private User customer;

    @Column(nullable = false)
    private LocalDateTime date;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private int quantity;

    // Getters and setters
}
