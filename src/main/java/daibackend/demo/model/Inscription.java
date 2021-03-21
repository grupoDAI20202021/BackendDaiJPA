package daibackend.demo.model;

import javax.persistence.*;
import java.util.Objects;

@Entity(name = "Inscription")
@Table(name = "Inscription")
@IdClass(InscriptionId.class)
public class Inscription {

    @Id
    @ManyToOne
    @JoinColumn(name = "id_child", referencedColumnName = "id_child", nullable = false)
    private Child id_child;

    @Id
    @ManyToOne
    @JoinColumn(name = "id_activity", referencedColumnName = "id_activity", nullable = false)
    private Activity id_activity;

    private int presence;

    private int evaluation;

    public Inscription() {
    }

    public Inscription(Child id_child, Activity id_activity, int presence, int evaluation) {
        this.id_child = id_child;
        this.id_activity = id_activity;
        this.presence = presence;
        this.evaluation = evaluation;
    }

    public Child getId_child() {
        return id_child;
    }

    public void setId_child(Child id_child) {
        this.id_child = id_child;
    }

    public Activity getId_activity() {
        return id_activity;
    }

    public void setId_activity(Activity id_activity) {
        this.id_activity = id_activity;
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
        return presence == that.presence && evaluation == that.evaluation && Objects.equals(id_child, that.id_child) && Objects.equals(id_activity, that.id_activity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_child, id_activity, presence, evaluation);
    }
}

