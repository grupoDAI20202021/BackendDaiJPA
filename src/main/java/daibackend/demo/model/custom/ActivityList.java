package daibackend.demo.model.custom;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

public class ActivityList {
    private String title;
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date init_data;
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date end_data;
    private String status;
    private String activityType;
    private long idActivity;

    public ActivityList() {
    }

    public ActivityList(String title, Date init_data, Date end_data, String status, String activityType, long idActivity) {
        this.title = title;
        this.init_data = init_data;
        this.end_data = end_data;
        this.status = status;
        this.activityType = activityType;
        this.idActivity = idActivity;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getActivityType() {
        return activityType;
    }

    public void setActivityType(String activityType) {
        this.activityType = activityType;
    }

    public long getIdActivity() {
        return idActivity;
    }

    public void setIdActivity(long idActivity) {
        this.idActivity = idActivity;
    }
}
