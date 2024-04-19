package freezy.entities;


import javax.persistence.*;

@Entity
@Table(name = "return_inventory")
public class ReturnInventory {
    @Id
    private String id;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(nullable = false)
    private int quantity;

    private String reason;

    // Getters and setters
}
