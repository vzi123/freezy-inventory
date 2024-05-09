package freezy.entities;



import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;


@Entity
@Table(name = "inventory_log")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InventoryLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @ManyToOne
    @JoinColumn(name = "inventory_id")
    private Inventory inventory;

    @Enumerated(EnumType.STRING)
    @JoinColumn(name = "in_out")
    private InventoryLogEntry inOut;

    @Column(nullable = false)
    private String comments;

    @Column(nullable = false)
    @JoinColumn(name = "created_at")
    private Timestamp createdAt;

}
