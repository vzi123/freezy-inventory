package freezy.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "dashboard_widgets")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DashBoardWidgets {
    @Id
    private String id;

    @Column(nullable = false)
    private String name;

    private String widgetCode;

    private Boolean isEnabled;

    private Integer sequence;
}
