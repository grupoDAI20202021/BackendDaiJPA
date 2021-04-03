package daibackend.demo.model;

import org.hibernate.annotations.NaturalId;

import javax.persistence.*;

@Entity(name = "role")
@Table(name = "role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRole;

    @Enumerated(EnumType.STRING)
    @NaturalId
    @Column(nullable = false, length = 60)
    private RoleName name;

    public Role() {
    }

    public Role(Long idRole, RoleName name) {
        this.idRole = idRole;
        this.name = name;
    }

    public void setIdRole(Long idRole) {
        this.idRole = idRole;
    }


    public Long getIdRole() {
        return idRole;
    }

    public RoleName getName() {
        return name;
    }

    public void setName(RoleName name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Role [idRole=" + idRole + "]";
    }
}
