package freezy.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "category_uom_map")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryUOMMap {
    @Id
    private String id;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Enumerated(EnumType.STRING)
    private UOM uom;

    private String multiple;

    // Getters and setters
}
