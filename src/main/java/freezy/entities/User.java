package freezy.entities;



import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "user")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    private String id;

    @Column(nullable = false)
    @JsonIgnore
    private String first_name;

    @Column(nullable = false)
    @JsonIgnore
    private String last_name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String phone_number;

    @Enumerated(EnumType.STRING)
    private UserRole role;


    @Column(nullable = false)
    @JsonIgnore
    private String created_at;

    // Getters and setters
}
