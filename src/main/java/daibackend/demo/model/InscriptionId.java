package daibackend.demo.model;


import java.io.Serializable;
import java.util.Objects;

public class InscriptionId implements Serializable {

    private Child child;

    private Activity activity;

    public InscriptionId() {

    }
    public InscriptionId(Child child, Activity activity) {
        this.child = child;
        this.activity = activity;
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




    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InscriptionId that = (InscriptionId) o;
        return Objects.equals(child, that.child) && Objects.equals(activity, that.activity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(child, activity);
    }
}
