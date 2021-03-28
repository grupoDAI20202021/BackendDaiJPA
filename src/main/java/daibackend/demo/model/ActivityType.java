package daibackend.demo.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import static daibackend.demo.util.ConstantUtils.CHAR_PATTERN;


@Entity(name = "activity_Type")
@Table(name = "activity_Type")
public class ActivityType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idActivityType;

    @NotBlank(message = "Can't be blank")
    @Pattern(regexp = CHAR_PATTERN, message = "Can only letters, letters with special characters and spaces")
    private String name;

    public ActivityType() {
    }

    public ActivityType(Long idActivityType, String name) {
        this.idActivityType = idActivityType;
        this.name = name;
    }

    public Long getIdActivityType() {
        return idActivityType;
    }

    public void setIdActivityType(Long idActivityType) {
        this.idActivityType = idActivityType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
