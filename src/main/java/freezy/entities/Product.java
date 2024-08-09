package freezy.entities;



import com.fasterxml.jackson.annotation.JsonManagedReference;
import freezy.entities.Brand;
import freezy.entities.Category;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "product_v1")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    private String id;

    @Column(nullable = false)
    private String name;

    private String description;

    private Integer cost;

    @Column
    private String hsnNo;

    @ManyToOne
    @JoinColumn(name = "category_id")
    @JsonManagedReference
    private Category category;

    @ManyToOne
    @JoinColumn(name = "brand_id")
    @JsonManagedReference
    private Brand brand;

}

