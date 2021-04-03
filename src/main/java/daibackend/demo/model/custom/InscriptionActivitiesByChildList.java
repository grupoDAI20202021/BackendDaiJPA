package daibackend.demo.model.custom;

import javax.persistence.Id;
import java.util.Date;

public class InscriptionActivitiesByChildList {
    @Id
    private long idActivity;

    private Date end_data;

    private int evaluation;

    private String title;

    private String name;

    public InscriptionActivitiesByChildList() {
    }

    public InscriptionActivitiesByChildList(long idActivity, Date end_data, int evaluation, String title, String name) {
        this.idActivity = idActivity;
        this.end_data = end_data;
        this.evaluation = evaluation;
        this.title = title;
        this.name = name;
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
