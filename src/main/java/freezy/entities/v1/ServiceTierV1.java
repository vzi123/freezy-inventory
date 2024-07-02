package freezy.entities.v1;



import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Entity
@Table(name = "service_tier_v1")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceTierV1 {
    @Id
    private String id;

    @Column(nullable = false)
    private String name;

    private String description;

    private ServiceTierTypeV1 type;

    @OneToMany(mappedBy = "serviceTier")
    @JsonBackReference
    @JsonIgnore
    private List<ServiceV1> services;

}

