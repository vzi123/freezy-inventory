package freezy.entities;


import javax.persistence.*;

@Entity
@Table(name = "category_uom_map")
public class CategoryUOMMap {
    @Id
    private String id;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Enumerated(EnumType.STRING)
    private UOM uom;

    // Getters and setters
}
