package daibackend.demo.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import daibackend.demo.util.ConstantUtils;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;

@Entity(name = "Activity")
@Table(name="\"Activity\"")
public class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_activity;

    @ManyToOne
    @JoinColumn(name = "id_institution", referencedColumnName = "id_institution", nullable = false)
    private Institution institution;

    @ManyToOne
    @JoinColumn(name = "id_activity_type", referencedColumnName = "id_activity_type", nullable = false)
    private ActivityType activityType;

    @ManyToOne
    @JoinColumn(name = "id_sponsor", referencedColumnName = "id_sponsor", nullable = false)
    private Sponsor sponsor;

    @NotBlank(message = "Can't be blank")
    @Pattern(regexp = ConstantUtils.CHAR_PATTERN, message = "Can only letters, letters with special characters and spaces")
    private String status;

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

    private int evaluation;

    private int spaces;

    public Activity() {
    }

    public Activity(Long id_activity, Institution institution, ActivityType activityType, Sponsor sponsor, String status, Date init_data, Date end_data, String address, int evaluation, int spaces) {
        this.id_activity = id_activity;
        this.institution = institution;
        this.activityType = activityType;
        this.sponsor = sponsor;
        this.status = status;
        this.init_data = init_data;
        this.end_data = end_data;
        this.address = address;
        this.evaluation = evaluation;
        this.spaces = spaces;
    }

    public Long getId_activity() {
        return id_activity;
    }

    public void setId_activity(Long id_activity) {
        this.id_activity = id_activity;
    }

    public Institution getInstitution() {
        return institution;
    }

    public void setInstitution(Institution institution) {
        this.institution = institution;
    }

    public ActivityType getActivityType() {
        return activityType;
    }

    public void setActivityType(ActivityType activityType) {
        this.activityType = activityType;
    }

    public Sponsor getSponsor() {
        return sponsor;
    }

    public void setSponsor(Sponsor sponsor) {
        this.sponsor = sponsor;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public int getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(int evaluation) {
        this.evaluation = evaluation;
    }

    public int getSpaces() {
        return spaces;
    }

    public void setSpaces(int spaces) {
        this.spaces = spaces;
    }
}
