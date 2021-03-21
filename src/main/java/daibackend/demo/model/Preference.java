package daibackend.demo.model;

import javax.persistence.*;

@Entity(name = "Preference")
@Table(name = "Preference")
public class Preference {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_preference;

    @ManyToOne
    @JoinColumn(name = "id_child", referencedColumnName = "id_child", nullable = false)
    private Child child;

    @ManyToOne
    @JoinColumn(name = "id_activity_type", referencedColumnName = "id_activity_type", nullable = false)
    private ActivityType activityType;

    public Preference() {
    }

    public Preference(Long id_preference, Child child, ActivityType activityType) {
        this.id_preference = id_preference;
        this.child = child;
        this.activityType = activityType;
    }

    public Long getId_preference() {
        return id_preference;
    }

    public void setId_preference(Long id_preference) {
        this.id_preference = id_preference;
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
