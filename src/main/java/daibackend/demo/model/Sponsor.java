package daibackend.demo.model;

import daibackend.demo.util.ConstantUtils;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Entity(name = "sponsor")
@Table(name = "sponsor")

public class Sponsor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idSponsor;

    @ManyToOne
    @JoinColumn(name = "idTownHall", referencedColumnName = "idTownHall", nullable = false)
    private TownHall townHall;

    @NotBlank(message = "Can't be blank")
    @Pattern(regexp = ConstantUtils.CHAR_PATTERN, message = "Can only letters, letters with special characters and spaces")
    private String name;

    @ManyToOne
    @JoinColumn(name = "idImage", referencedColumnName = "idImage", nullable = false)
    private Image image;

    public Sponsor() {
    }

    public Sponsor(Long idSponsor, TownHall townHall, String name, Image image) {
        this.idSponsor = idSponsor;
        this.townHall = townHall;
        this.name = name;
        this.image = image;
    }

    public Long getIdSponsor() {
        return idSponsor;
    }

    public void setIdSponsor(Long id_sponsor) {
        this.idSponsor = id_sponsor;
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

    public Image getImageExample() {
        return image;
    }

    public void setImageExample(Image image) {
        this.image = image;
    }
}
