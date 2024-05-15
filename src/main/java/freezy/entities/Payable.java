package freezy.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "payables")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Payable {

    @Id
    private String id;

    @ManyToOne
    @JoinColumn(name = "created_by")
    private User createdBy;

    @JoinColumn(name = "created_at")
    @Column(nullable = false)
    private String createdAt;

    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @Column(nullable = false)
    private Integer amount;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User vendor;


    @Column(nullable = false)
    private PayableStatus status;

    @Column
    private String comments;

    @ManyToOne
    @JoinColumn(name = "po_id")
    @JsonIgnore
    private PurchaseOrder purchaseOrder;

}