package freezy.entities;



import jakarta.persistence.*;
import lombok.ToString;

import java.util.List;

@Entity
@Table(name = "category")
@ToString
public class Category {
    @Id
    private String id;

    @Column(nullable = false)
    private String name;

    private String description;

    @ManyToOne
    @JoinColumn(name = "created_by")
    private User createdBy;

    @ManyToOne
    @JoinColumn(name = "updated_by")
    private User updatedBy;

    @Column(nullable = false)
    private String created_at;

    @Column(nullable = false)
    private String updated_at;

    @OneToMany(mappedBy = "category")
    private List<Product> products;

    // Getters and setters
}
