package freezy.entities.v1;



import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "service_v1")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceV1 {
    @Id
    private String id;

    @Column(nullable = false)
    private String name;

    private String description;

    private Integer cost;

    @ManyToOne
    @JoinColumn(name = "category_id")
    @JsonManagedReference
    private CategoryV1 category;

    @ManyToOne
    @JoinColumn(name = "service_tier_id")
    @JsonManagedReference
    private ServiceTierV1 serviceTier;

}

