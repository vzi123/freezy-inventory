package freezy.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "stock_alerts")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockAlerts {
    @Id
    private String id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product_old productOld;

    @Column(nullable = false)
    @JoinColumn(name = "alert_quantity")
    private Integer alertQuantity;

}
