package freezy.entities;



import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "project")
public class Project {
    @Id
    private String id;

    @Column(nullable = false)
    private String name;

    private String location;

    @Enumerated(EnumType.STRING)
    private ProjectStatus status;

    @ManyToOne
    @JoinColumn(name = "customer")
    private User customer;

    @ManyToOne
    @JoinColumn(name = "created_by")
    private User createdBy;

    @Column(nullable = false)
    private LocalDateTime created_at;

    // Getters and setters
}
