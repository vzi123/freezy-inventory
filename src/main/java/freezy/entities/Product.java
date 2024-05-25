package freezy.entities;



import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "product")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    private String id;

    @Column(nullable = false)
    private String name;

    private String description;

    @ManyToOne
    @JoinColumn(name = "category_id")
    @JsonManagedReference
    @JsonIgnore
    private Category category;

//    @ManyToOne
//    @JoinColumn(name = "created_by")
//    private User createdBy;
//
//    @ManyToOne
//    @JoinColumn(name = "updated_by")
//    private User updatedBy;
//
//    @ManyToOne
//    @JoinColumn(name = "deleted_by")
//    private User deletedBy;
//
//    @Column(nullable = false)
//    private String created_at;
//
//    @Column(nullable = false)
//    private String updated_at;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }


    // Getters and setters

    public String toString(){
        return this.name;
    }
}

