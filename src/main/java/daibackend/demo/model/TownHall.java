package daibackend.demo.model;

import daibackend.demo.util.ConstantUtils;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Entity(name = "Town_Hall")
@Table(name = "Town_Hall")
public class TownHall {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_town_hall;

    @ManyToOne
    @JoinColumn(name = "id_login", referencedColumnName = "id_login", nullable = false)
    private Login login;

    @NotBlank(message = "Can't be blank")
    @Pattern(regexp = ConstantUtils.CHAR_PATTERN, message = "Can only letters, letters with special characters and spaces")
    private String name;

    @NotBlank(message = "Can't be blank")
    @Pattern(regexp = ConstantUtils.ADDRESS_PATTERN, message = "Can only letters, letters with special characters, numbers and special characters (\",\", \"º\", \" \")")
    private String address;

    public TownHall() {
    }

    public TownHall(Long id_town_hall, Login login,String name, String address) {
        this.id_town_hall = id_town_hall;
        this.login = login;
        this.name = name;
        this.address = address;
    }

    public Long getId_town_hall() {
        return id_town_hall;
    }

    public void setId_town_hall(Long id_town_hall) {
        this.id_town_hall = id_town_hall;
    }

    public Login getLogin() {
        return login;
    }

    public void setLogin(Login login) {
        this.login = login;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
