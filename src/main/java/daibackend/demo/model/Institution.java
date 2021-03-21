package daibackend.demo.model;

import daibackend.demo.util.ConstantUtils;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;


@Entity(name = "Institution")
@Table(name = "Institution")

public class Institution {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_institution;

    @ManyToOne
    @JoinColumn(name = "id_login", referencedColumnName = "id_login", nullable = false)
    private Login login;

    @ManyToOne
    @JoinColumn(name = "id_town_hall", referencedColumnName = "id_town_hall", nullable = false)
    private TownHall townHall;

    @NotBlank(message = "Can't be blank")
    @Pattern(regexp = ConstantUtils.CHAR_PATTERN, message = "Can only letters, letters with special characters and spaces")
    private String name;

    @NotBlank(message = "Can't be blank")
    @Pattern(regexp = ConstantUtils.ADDRESS_PATTERN, message = "Can only letters, letters with special characters, numbers and special characters (\",\", \"º\", \" \")")
    private String address;

    public Institution() {
    }

    public Institution(Long id_institution, Login login, TownHall townHall, String name, String address) {
        this.id_institution = id_institution;
        this.login = login;
        this.townHall = townHall;
        this.name = name;
        this.address = address;
    }

    public Long getId_institution() {
        return id_institution;
    }

    public void setId_institution(Long id_institution) {
        this.id_institution = id_institution;
    }

    public Login getLogin() {
        return login;
    }

    public void setLogin(Login login) {
        this.login = login;
    }

    public TownHall getTownHall() {
        return townHall;
    }

    public void setTownHall(TownHall townHall) {
        this.townHall = townHall;
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
