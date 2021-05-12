package daibackend.demo.model.custom;

import com.fasterxml.jackson.annotation.JsonFormat;
import daibackend.demo.util.ConstantUtils;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import java.util.Date;

import static daibackend.demo.util.ConstantUtils.CHAR_PATTERN;

public class PreferenceActivitiesByChild {

    private long idActivity;

    @NotBlank(message = "Can't be blank")
    @Pattern(regexp = CHAR_PATTERN, message = "Can only letters, letters with special characters and spaces")
    private String name;

    @NotBlank(message = "Can't be blank")
    @Pattern(regexp = CHAR_PATTERN, message = "Can only letters, letters with special characters and spaces")
    private String title;

    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @NotNull(message = "Can't be null")
    private Date init_data;

    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @NotNull(message = "Can't be null")
    private Date end_data;

    @NotBlank(message = "Can't be blank")
    @Pattern(regexp = ConstantUtils.ADDRESS_PATTERN, message = "Can only letters, letters with special characters, numbers and special characters (\",\", \"º\", \" \")")
    private String address;

    public PreferenceActivitiesByChild() {
    }

    public PreferenceActivitiesByChild(long idActivity, @NotBlank(message = "Can't be blank") @Pattern(regexp = CHAR_PATTERN, message = "Can only letters, letters with special characters and spaces") String name, @NotBlank(message = "Can't be blank") @Pattern(regexp = CHAR_PATTERN, message = "Can only letters, letters with special characters and spaces") String title, @NotNull(message = "Can't be null") Date init_data, @NotNull(message = "Can't be null") Date end_data, @NotBlank(message = "Can't be blank") @Pattern(regexp = ConstantUtils.ADDRESS_PATTERN, message = "Can only letters, letters with special characters, numbers and special characters (\",\", \"º\", \" \")") String address) {
        this.idActivity = idActivity;
        this.name = name;
        this.title = title;
        this.init_data = init_data;
        this.end_data = end_data;
        this.address = address;
    }

    public long getIdActivity() {
        return idActivity;
    }

    public void setIdActivity(long idActivity) {
        this.idActivity = idActivity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public  void  setTitle(String title) {
        this.title = title;
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
}
