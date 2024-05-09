package freezy.entities;



import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Entity
@Table(name = "category")
@ToString
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column(nullable = false)
    private String name;

    private String description;

//    @ManyToOne
//    @JoinColumn(name = "created_by")
//    private User createdBy;
//
//    @ManyToOne
//    @JoinColumn(name = "updated_by")
//    private User updatedBy;
//
//    @Column(nullable = false)
//    private String created_at;
//
//    @Column(nullable = false)
//    private String updated_at;

    @OneToMany(mappedBy = "category")
    private List<Product> products;

    // Getters and setters
}