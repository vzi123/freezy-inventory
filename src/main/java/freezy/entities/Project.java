package freezy.entities;



import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name = "project")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Project {
    @Id
    private String id;

    @Column(nullable = false)
    private String name;

    @JsonIgnore
    private String location;

    @Enumerated(EnumType.STRING)
    private ProjectStatus status;

    @ManyToOne
    @JoinColumn(name = "customer")
    @JsonIgnore
    private User customer;

    @ManyToOne
    @JoinColumn(name = "contact_person")
    @JsonIgnore
    private User contactPerson;

    @ManyToOne
    @JoinColumn(name = "created_by")
    @JsonIgnore
    private User createdBy;

    @Column(nullable = false)
    @JoinColumn(name = "created_at")
    @JsonIgnore
    private String createdAt;

    // Getters and setters
}
