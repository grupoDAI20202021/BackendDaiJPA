package daibackend.demo.model;

import daibackend.demo.util.ConstantUtils;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Entity(name = "Child")
@Table(name = "Child")
public class Child {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_child;

    @ManyToOne
    @JoinColumn(name = "id_login", referencedColumnName = "id_login", nullable = false)
    private Login login;

    @NotBlank(message = "Can't be blank")
    @Pattern(regexp = ConstantUtils.CHAR_PATTERN, message = "Can only letters, letters with special characters and spaces")
    private String name;

    private int age;

    public Child() {
    }

    public Child(Long id_child, Login login, String name, int age) {
        this.id_child = id_child;
        this.login = login;
        this.name = name;
        this.age = age;
    }

    public Long getId_child() {
        return id_child;
    }

    public void setId_child(Long id_child) {
        this.id_child = id_child;
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
