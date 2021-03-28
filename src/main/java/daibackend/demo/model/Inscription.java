package daibackend.demo.model;

import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity(name = "inscription")
@Table(name = "inscription")
@IdClass(InscriptionId.class)
public class Inscription implements Serializable {

    @Id
    @ManyToOne
    @JoinColumn(name = "idChild", referencedColumnName = "idChild", nullable = false)
    private Child child;

    @Id
    @ManyToOne
    @JoinColumn(name = "idActivity", referencedColumnName = "idActivity", nullable = false)
    private Activity activity;

    private int presence;

    private int evaluation;

    public Inscription() {
    }

    public Inscription(Child child, Activity activity, int presence, int evaluation) {
        this.child = child;
        this.activity = activity;
        this.presence = presence;
        this.evaluation = evaluation;
    }

    public Child getChild() {
        return child;
    }

    public void setChild(Child id_child) {
        this.child = id_child;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity id_activity) {
        this.activity = id_activity;
    }

    public int getPresence() {
        return presence;
    }

    public void setPresence(int presence) {
        this.presence = presence;
    }

    public int getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(int evaluation) {
        this.evaluation = evaluation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Inscription that = (Inscription) o;
        return presence == that.presence && evaluation == that.evaluation && Objects.equals(child, that.child) && Objects.equals(activity, that.activity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(child, activity, presence, evaluation);
    }
}

