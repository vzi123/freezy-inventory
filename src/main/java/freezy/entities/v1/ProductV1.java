package freezy.entities.v1;



import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "product_v1")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductV1 {
    @Id
    private String id;

    @Column(nullable = false)
    private String name;

    private String description;

    @JsonIgnore
    private Integer cost;

    @ManyToOne
    @JoinColumn(name = "category_id")
    @JsonManagedReference
    private CategoryV1 category;

}

