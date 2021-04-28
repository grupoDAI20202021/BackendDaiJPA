package daibackend.demo.model.custom;

import com.fasterxml.jackson.annotation.JsonFormat;
import daibackend.demo.model.Sponsor;
import daibackend.demo.util.ConstantUtils;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;

public class updateActivityTownHall {

    private long idSponsor;

    public updateActivityTownHall() {
    }

    public updateActivityTownHall(long idSponsor) {
        this.idSponsor = idSponsor;
    }

    public long getIdSponsor() {
        return idSponsor;
    }

    public void setIdSponsor(long idSponsor) {
        this.idSponsor = idSponsor;
    }
}
