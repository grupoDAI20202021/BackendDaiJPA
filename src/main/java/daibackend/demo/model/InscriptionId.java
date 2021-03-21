package daibackend.demo.model;


import java.io.Serializable;
import java.util.Objects;

public class InscriptionId implements Serializable {

    private Child id_child;

    private Activity id_activity;

    public InscriptionId() {

    }
    public InscriptionId(Child id_child, Activity id_activity) {
        this.id_child = id_child;
        this.id_activity = id_activity;
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




    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InscriptionId that = (InscriptionId) o;
        return Objects.equals(id_child, that.id_child) && Objects.equals(id_activity, that.id_activity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_child, id_activity);
    }
}
