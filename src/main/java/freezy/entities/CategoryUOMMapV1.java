package freezy.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "category_uom_map_v1")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryUOMMapV1 {
    @Id
    private String id;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Enumerated(EnumType.STRING)
    private UOMV1 uomv1;

    private String multiple;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "accessory_id")
    private Accessory accessory;

    @ManyToOne
    @JoinColumn(name = "service_id")
    private Service service;

    // Getters and setters
}
