package daibackend.demo.model;

import daibackend.demo.util.ConstantUtils;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Entity(name = "townHall")
@Table(name = "town_hall")
public class TownHall {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTownHall;

    @ManyToOne
    @JoinColumn(name = "idLogin", referencedColumnName = "idLogin", nullable = false)
    private Login login;

    @NotBlank(message = "Can't be blank")
    @Pattern(regexp = ConstantUtils.CHAR_PATTERN, message = "Can only letters, letters with special characters and spaces")
    private String name;

    @NotBlank(message = "Can't be blank")
    @Pattern(regexp = ConstantUtils.ADDRESS_PATTERN, message = "Can only letters, letters with special characters, numbers and special characters (\",\", \"º\", \" \")")
    private String address;

    private int active;

    public TownHall() {
    }

    public TownHall(Long idTownHall, Login login, String name, String address) {
        this.idTownHall = idTownHall;
        this.login = login;
        this.name = name;
        this.address = address;
        this.active=1;
    }

    public Long getIdTownHall() {
        return idTownHall;
    }

    public void setIdTownHall(Long id_town_hall) {
        this.idTownHall = id_town_hall;
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

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }
}
