package freezy.entities.v1;


import freezy.entities.Category;
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
    private CategoryV1 category;

    @Enumerated(EnumType.STRING)
    private UOMV1 uomv1;

    private String multiple;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductV1 product;

    @ManyToOne
    @JoinColumn(name = "accessory_id")
    private AccessoryV1 accessory;

    @ManyToOne
    @JoinColumn(name = "service_id")
    private ServiceV1 service;

    // Getters and setters
}
