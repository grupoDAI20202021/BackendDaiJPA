package daibackend.demo.model;

import daibackend.demo.util.ConstantUtils;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Entity(name = "Sponsor")
@Table(name = "Sponsor")

public class Sponsor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_sponsor;

    @ManyToOne
    @JoinColumn(name = "id_town_hall", referencedColumnName = "id_town_hall", nullable = false)
    private TownHall townHall;

    @NotBlank(message = "Can't be blank")
    @Pattern(regexp = ConstantUtils.CHAR_PATTERN, message = "Can only letters, letters with special characters and spaces")
    private String name;

    @ManyToOne
    @JoinColumn(name = "id_image", referencedColumnName = "id_image", nullable = false)
    private ImageExample imageExample;

    public Sponsor() {
    }

    public Sponsor(Long id_sponsor, TownHall townHall,  String name, ImageExample imageExample) {
        this.id_sponsor = id_sponsor;
        this.townHall = townHall;
        this.name = name;
        this.imageExample = imageExample;
    }

    public Long getId_sponsor() {
        return id_sponsor;
    }

    public void setId_sponsor(Long id_sponsor) {
        this.id_sponsor = id_sponsor;
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

    public ImageExample getImageExample() {
        return imageExample;
    }

    public void setImageExample(ImageExample imageExample) {
        this.imageExample = imageExample;
    }
}
