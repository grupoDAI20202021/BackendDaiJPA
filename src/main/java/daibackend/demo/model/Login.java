package daibackend.demo.model;



import daibackend.demo.util.ConstantUtils;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Entity(name = "login")
@Table(name = "login")

public class Login {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_login;

    @Column(unique = true)
    @Email(message = "Insert a valid email")
    private String email;

    @NotBlank(message = "Can't be blank")
    @Pattern(regexp = daibackend.demo.util.ConstantUtils.PASSWORD_PATTERN, message = "Needs at least 1 UpperCase, 1 LowerCase and 1 Number")
    private String password;

    @NotBlank(message = "Can't be blank")
    @Pattern(regexp = ConstantUtils.CHAR_PATTERN, message = "Allow only letters, letters with special characters and spaces")
    private String profile;

    public Login() {
    }

    public Login(Long id_login, String email, String password,String profile) {
        this.id_login = id_login;
        this.email = email;
        this.password = password;
        this.profile = profile;
    }

    public Long getId_login() {
        return id_login;
    }

    public void setId_login(Long id_login) {
        this.id_login = id_login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }
}
