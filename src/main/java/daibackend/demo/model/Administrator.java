package daibackend.demo.model;

import daibackend.demo.util.ConstantUtils;
import javax.validation.constraints.Pattern;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity(name = "Administrator")
@Table(name = "Administrator")

public class Administrator {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_administrator;

    @ManyToOne
    @JoinColumn(name = "id_login", referencedColumnName = "id_login", nullable = false)
    private Login login;

    @NotBlank(message = "Can't be blank")
    @Pattern(regexp = ConstantUtils.CHAR_PATTERN, message = "Can only letters, letters with special characters and spaces")
    private String name;

    @NotBlank(message = "Can't be blank")
    @Size(max = 9, min = 9, message = "Must contain exacly 9 numbers")
    @Pattern(regexp = ConstantUtils.CODE_PATTERN, message = "Can only contain numbers")
    private String contact;

    public Administrator() {
    }

    public Long getId_administrator() {
        return id_administrator;
    }

    public void setId_administrator(Long id_administrator) {
        this.id_administrator = id_administrator;
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

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public Administrator(Long id_administrator, Login login, String name, String contact) {
        this.id_administrator = id_administrator;
        this.login = login;
        this.name = name;
        this.contact = contact;
    }
}
