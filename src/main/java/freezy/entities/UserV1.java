package freezy.entities;



import com.fasterxml.jackson.annotation.JsonIgnore;
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

    private String firstName;

    @Column(nullable = false)

    private String lastName;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private UserRoleV1 role;


    @Column(nullable = false)
    @JsonIgnore
    private String createdAt;

    @Column
    private String address;

    @Column
    private String city;

    @Column
    private String gstId;

    @Column
    private String pincode;


    @Column
    private String secretKey;
}
