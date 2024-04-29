package freezy.entities;




import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity(name = "delivery")
@Table(name = "delivery")
@Data
@NoArgsConstructor
@AllArgsConstructor
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
    private String date;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private int quantity;

    // Getters and setters
}
