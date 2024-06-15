package freezy.entities.v1;



import com.fasterxml.jackson.annotation.JsonIgnore;
import freezy.entities.UserRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "user_v1")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserV1 {
    @Id
    private String id;

    @Column(nullable = false)

    private String first_name;

    @Column(nullable = false)

    private String last_name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String phone_number;

    @Enumerated(EnumType.STRING)
    private UserRoleV1 role;


    @Column(nullable = false)
    @JsonIgnore
    private String created_at;

    @Column
    private String address;

    @Column
    private String city;
}
