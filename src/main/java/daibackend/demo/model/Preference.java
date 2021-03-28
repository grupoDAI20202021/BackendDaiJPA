package daibackend.demo.model;

import javax.persistence.*;

@Entity(name = "preference")
@Table(name = "preference")
public class Preference {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPreference;

    @ManyToOne
    @JoinColumn(name = "idChild", referencedColumnName = "idChild", nullable = false)
    private Child child;

    @ManyToOne
    @JoinColumn(name = "idActivityType", referencedColumnName = "idActivityType", nullable = false)
    private ActivityType activityType;

    public Preference() {
    }

    public Preference(Long idPreference, Child child, ActivityType activityType) {
        this.idPreference = idPreference;
        this.child = child;
        this.activityType = activityType;
    }

    public Long getIdPreference() {
        return idPreference;
    }

    public void setIdPreference(Long id_preference) {
        this.idPreference = id_preference;
    }

    public Child getChild() {
        return child;
    }

    public void setChild(Child child) {
        this.child = child;
    }

    public ActivityType getActivityType() {
        return activityType;
    }

    public void setActivityType(ActivityType activityType) {
        this.activityType = activityType;
    }
}
