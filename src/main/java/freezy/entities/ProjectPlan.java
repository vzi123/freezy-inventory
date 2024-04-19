package freezy.entities;



import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "project_plan")
public class ProjectPlan {
    @Id
    private String id;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @Enumerated(EnumType.STRING)
    private ProjectPhase phase;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(nullable = false)
    private int units;

    @ManyToOne
    @JoinColumn(name = "created_by")
    private User createdBy;

    @Column(nullable = false)
    private LocalDateTime created_at;

    // Getters and setters
}
