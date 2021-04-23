package daibackend.demo.model.custom;

import com.fasterxml.jackson.annotation.JsonFormat;
import daibackend.demo.model.ActivityType;
import daibackend.demo.model.Institution;
import daibackend.demo.util.ConstantUtils;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;

public class CreateActivity {

    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @NotNull(message = "Can't be null")
    private Date init_data;

    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @NotNull(message = "Can't be null")
    private Date end_data;

    @NotBlank(message = "Can't be blank")
    @Pattern(regexp = ConstantUtils.ADDRESS_PATTERN, message = "Can only letters, letters with special characters, numbers and special characters (\",\", \"º\", \" \")")
    private String address;


    private int spaces;

    @NotBlank(message = "Can't be blank")
    @Pattern(regexp = ConstantUtils.CHAR_PATTERN, message = "Can only letters, letters with special characters and spaces")
    private String title;

    private long idInstitution;

    private long idActivityType;

    public CreateActivity(@NotNull(message = "Can't be null") Date init_data, @NotNull(message = "Can't be null") Date end_data, @NotBlank(message = "Can't be blank") @Pattern(regexp = ConstantUtils.ADDRESS_PATTERN, message = "Can only letters, letters with special characters, numbers and special characters (\",\", \"º\", \" \")") String address, int spaces, @NotBlank(message = "Can't be blank") @Pattern(regexp = ConstantUtils.CHAR_PATTERN, message = "Can only letters, letters with special characters and spaces") String title, long idInstitution, long idActivityType) {
        this.init_data = init_data;
        this.end_data = end_data;
        this.address = address;
        this.spaces = spaces;
        this.title = title;
        this.idInstitution = idInstitution;
        this.idActivityType = idActivityType;
    }

    public Date getInit_data() {
        return init_data;
    }

    public void setInit_data(Date init_data) {
        this.init_data = init_data;
    }

    public Date getEnd_data() {
        return end_data;
    }

    public void setEnd_data(Date end_data) {
        this.end_data = end_data;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getSpaces() {
        return spaces;
    }

    public void setSpaces(int spaces) {
        this.spaces = spaces;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getIdInstitution() {
        return idInstitution;
    }

    public void setIdInstitution(long idInstitution) {
        this.idInstitution = idInstitution;
    }

    public long getIdActivityType() {
        return idActivityType;
    }

    public void setIdActivityType(long idActivityType) {
        this.idActivityType = idActivityType;
    }
}
