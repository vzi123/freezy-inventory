package freezy.entities.v1;



import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "inventory_log_v1")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InventoryLogV1 {
    @Id
    private String id;

    @ManyToOne
    @JoinColumn(name = "inventory_id")
    private InventoryV1 inventory;

    @Enumerated(EnumType.STRING)
    @JoinColumn(name = "in_out")
    private InventoryLogEntryV1 inOut;

    @Column(nullable = false)
    private String comments;

    @Column
    private Integer quantity;

    @Column
    private Integer updatedStock;

    @Column(nullable = false)
    @JoinColumn(name = "created_at")
    private String createdAt;

    @Column(nullable = false)
    private Integer amount;


}
