package freezy.entities;



import jakarta.persistence.*;


import java.time.LocalDateTime;

@Entity
@Table(name = "product")
public class Product {
    @Id
    private String id;

    @Column(nullable = false)
    private String name;

    private String description;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

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
    private LocalDateTime created_at;

    @Column(nullable = false)
    private LocalDateTime updated_at;

    @Column(nullable = false)
    private LocalDateTime deleted_at;

    // Getters and setters
}

