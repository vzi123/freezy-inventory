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

    private String first_name;

    @Column(nullable = false)

    private String last_name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String phone_number;

    @Enumerated(EnumType.STRING)
    @JsonIgnore
    private UserRole role;


    @Column(nullable = false)
    @JsonIgnore
    private String created_at;

    @Column
    @JsonIgnore
    private String address;

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    // Getters and setters
}
