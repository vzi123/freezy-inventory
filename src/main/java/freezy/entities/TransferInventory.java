package freezy.entities;



import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "transfer_inventory")
public class TransferInventory {
    @Id
    private String id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(nullable = false)
    private int count;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private LocalDateTime date;

    private String reason;

    // Getters and setters
}
