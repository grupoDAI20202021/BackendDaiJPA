package daibackend.demo.model;



import daibackend.demo.util.ConstantUtils;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Collection;

@Entity(name = "login")
@Table(name = "login")

public class Login {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idLogin;

    @Column(unique = true)
    @Email(message = "Insert a valid email")
    private String email;

    @NotBlank(message = "Can't be blank")
    @Pattern(regexp = ConstantUtils.PASSWORD_PATTERN, message = "Needs at least 1 UpperCase, 1 LowerCase and 1 Number")
    private String password;

    @OneToOne
    @JoinColumn(name = "idRole", referencedColumnName = "idRole", nullable = true)
    private Role role;

    private int active;

    private int generatedCode;

    public Login() {
    }

    public Login(Long idLogin, @Email(message = "Insert a valid email") String email, @NotBlank(message = "Can't be blank") @Pattern(regexp = ConstantUtils.PASSWORD_PATTERN, message = "Needs at least 1 UpperCase, 1 LowerCase and 1 Number") String password, Role role,int active) {
        this.idLogin = idLogin;
        this.email = email;
        this.password = password;
        this.role = role;
        this.active=active;
    }

    public Login(Long idLogin, @Email(message = "Insert a valid email") String email, @NotBlank(message = "Can't be blank") @Pattern(regexp = ConstantUtils.PASSWORD_PATTERN, message = "Needs at least 1 UpperCase, 1 LowerCase and 1 Number") String password, Role role,int active,int generatedCode) {
        this.idLogin = idLogin;
        this.email = email;
        this.password = password;
        this.role = role;
        this.active=active;
        this.generatedCode=generatedCode;
    }

    public Long getIdLogin() {
        return idLogin;
    }

    public void setIdLogin(Long idLogin) {
        this.idLogin = idLogin;
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public int getGeneratedCode() {
        return generatedCode;
    }

    public void setGeneratedCode(int generatedCode) {
        this.generatedCode = generatedCode;
    }
}
