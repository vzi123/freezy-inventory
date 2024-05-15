package freezy.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "endorsement")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Endorsement {

    @Id
    private String id;


    @ManyToOne
    @JoinColumn(name = "po_id", nullable = false)
    @JsonIgnore
    private PurchaseOrder purchaseOrder;

    @Column(nullable = false)
    Integer budget;

    @ManyToOne
    @JoinColumn(name = "created_by")
    @JsonIgnore
    private User createdBy;

    @JoinColumn(name = "created_at")
    @Column(nullable = false)
    @JsonIgnore
    private String createdAt;

}
