package daibackend.demo.model;



import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import static daibackend.demo.util.ConstantUtils.CHAR_PATTERN;


@Entity(name = "Activity_Type")
@Table(name = "Activity_Type")
public class ActivityType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_activity_type;

    @NotBlank(message = "Can't be blank")
    @Pattern(regexp = CHAR_PATTERN, message = "Can only letters, letters with special characters and spaces")
    private String name;

    public ActivityType() {
    }

    public ActivityType(Long id_activity_type, String name) {
        this.id_activity_type = id_activity_type;
        this.name = name;
    }

    public Long getId_activity_type() {
        return id_activity_type;
    }

    public void setId_activity_type(Long id_activity_type) {
        this.id_activity_type = id_activity_type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
