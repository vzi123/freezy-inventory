package freezy.entities.v1;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "consignment_v1")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConsignmentV1 {

    @Id
    private String id;

    @Enumerated(EnumType.STRING)
    @JoinColumn(name = "in_out")
    private InventoryLogEntryV1 inOut;

    @Column(nullable = false)
    private String comments;

    @Column
    private Integer productCount;

    @Column(nullable = false)
    @JoinColumn(name = "created_at")
    private String createdAt;

    @Column(nullable = false)
    private Integer totalAmount;

    @ManyToOne
    @JoinColumn(name = "created_for")
    @JsonIgnore
    private UserV1 createdFor;
}
