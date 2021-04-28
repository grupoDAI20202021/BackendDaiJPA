package daibackend.demo.model;

import daibackend.demo.util.ConstantUtils;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity(name = "child")
@Table(name = "child")
public class Child {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idChild;

    @ManyToOne
    @JoinColumn(name = "idLogin", referencedColumnName = "idLogin", nullable = false)
    private Login login;

    @NotBlank(message = "Can't be blank")
    @Pattern(regexp = ConstantUtils.CHAR_PATTERN, message = "Can only letters, letters with special characters and spaces")
    private String name;

    private int age;

    @NotBlank(message = "Can't be blank")
    @Pattern(regexp = ConstantUtils.CHAR_PATTERN, message = "Can only letters, letters with special characters and spaces")
    private String address;

    @NotBlank(message = "Can't be blank")
    @Size(max = 9, min = 9, message = "Must contain exacly 9 numbers")
    @Pattern(regexp = ConstantUtils.CODE_PATTERN, message = "Can only contain numbers")
    private String contact;

    private long idAvatar;

    @Email(message = "Insert a valid email")
    private String parentEmail;



    public Child() {
    }

    public Child(Long idChild, Login login, String name, int age,String address,String contact,long idAvatar) {
        this.idChild = idChild;
        this.login = login;
        this.name = name;
        this.age = age;
        this.address=address;
        this.contact=contact;
        this.idAvatar=idAvatar;
    }

    public Child(Long idChild, Login login, String name, int age,String address,String contact,long idAvatar,String parentEmail) {
        this.idChild = idChild;
        this.login = login;
        this.name = name;
        this.age = age;
        this.address=address;
        this.contact=contact;
        this.idAvatar=idAvatar;
        this.parentEmail=parentEmail;
    }

    public Long getIdChild() {
        return idChild;
    }

    public void setIdChild(Long id_child) {
        this.idChild = id_child;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public long getIdAvatar() {
        return idAvatar;
    }

    public void setIdAvatar(long idAvatar) {
        this.idAvatar = idAvatar;
    }

    public String getParentEmail() {
        return parentEmail;
    }

    public void setParentEmail(String parentEmail) {
        this.parentEmail = parentEmail;
    }

}
