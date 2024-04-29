package freezy.entities;



import jakarta.persistence.*;


@Entity
@Table(name = "inventory")
public class Inventory {
    @Id
    private String id;

    @Column(nullable = false)
    private int inventory;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "created_by")
    private User createdBy;

    @ManyToOne
    @JoinColumn(name = "updated_by")
    private User updatedBy;

    @ManyToOne
    @JoinColumn(name = "deleted_by")
    private User deletedBy;

    @Column(nullable = false)
    private String created_at;

    @Column(nullable = false)
    private String updated_at;



    // Getters and setters
}
