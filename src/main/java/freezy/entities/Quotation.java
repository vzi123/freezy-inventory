package freezy.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "quotation")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Quotation {

    @Id
    private String id;

    public User getUser() {
        return user;
    }

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String userPersona;

    @Column(nullable = false)
    private Integer budget;

    @Column(nullable = false)
    private String status;

    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @ManyToOne
    @JoinColumn(name = "created_by")
    private User createdBy;

    @JoinColumn(name = "created_at")
    @Column(nullable = false)
    private String createdAt;

    @OneToMany(mappedBy = "quotation")
    @JsonManagedReference
    private List<QuotationItems> quotationItems;

}
