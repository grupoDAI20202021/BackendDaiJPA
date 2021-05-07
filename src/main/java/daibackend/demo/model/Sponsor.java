package daibackend.demo.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import daibackend.demo.util.ConstantUtils;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;

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

    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @NotNull(message = "Can't be null")
    private Date insert_data;

    private int active;
    public Sponsor() {
    }

    public Sponsor(Long idSponsor, TownHall townHall, String name, Image image,Date insert_data,int active) {
        this.idSponsor = idSponsor;
        this.townHall = townHall;
        this.name = name;
        this.image = image;
        this.insert_data=insert_data;
        this.active=active;
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

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Date getInsert_data() {
        return insert_data;
    }

    public void setInsert_data(Date insert_data) {
        this.insert_data = insert_data;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }
}
