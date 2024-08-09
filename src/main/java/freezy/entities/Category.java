package freezy.entities;



import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "category_v1")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Category {
    @Id
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
    @JsonBackReference
    @JsonIgnore
    private List<Product> products;

    @OneToMany(mappedBy = "category")
    @JsonBackReference
    @JsonIgnore
    private List<Accessory> accessories;

    @OneToMany(mappedBy = "category")
    @JsonBackReference
    @JsonIgnore
    private List<Service> services;

    // Getters and setters
}
