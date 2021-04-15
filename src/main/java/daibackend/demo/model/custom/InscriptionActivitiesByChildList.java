package daibackend.demo.model.custom;

import javax.persistence.Id;
import java.util.Date;

public class InscriptionActivitiesByChildList {
    @Id
    private long idActivity;

    private Date init_data;

    private Date end_data;

    private int evaluation;

    private String title;

    private String name;

    public InscriptionActivitiesByChildList() {
    }

    public InscriptionActivitiesByChildList(long idActivity, Date init_data, Date end_data, int evaluation, String title, String name) {
        this.idActivity = idActivity;
        this.init_data = init_data;
        this.end_data = end_data;
        this.evaluation = evaluation;
        this.title = title;
        this.name = name;
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

    public long getIdActivity() {
        return idActivity;
    }

    public void setIdActivity(long idActivity) {
        this.idActivity = idActivity;
    }

    public Date getEnd_date() {
        return end_data;
    }

    public void setEnd_date(Date end_date) {
        this.end_data = end_date;
    }

    public int getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(int evaluation) {
        this.evaluation = evaluation;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
