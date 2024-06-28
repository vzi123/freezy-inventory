package freezy.entities.v1;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "brand_v1")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BrandV1 {
    @Id
    private String id;

    @Column(nullable = false)
    private String name;

    private String description;

    @OneToMany(mappedBy = "brand")
    @JsonBackReference
    @JsonIgnore
    private List<ProductV1> products;

    // Getters and setters
}
