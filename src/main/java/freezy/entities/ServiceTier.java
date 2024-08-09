package freezy.entities;



import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class ServiceTier {
    @Id
    private String id;

    @Column(nullable = false)
    private String name;

    private String description;

    private ServiceTierType type;

    @OneToMany(mappedBy = "serviceTier")
    @JsonBackReference
    @JsonIgnore
    private List<Service> services;

}

