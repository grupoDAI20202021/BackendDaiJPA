package daibackend.demo.model.custom;

public class DataActivityType {
    private int quantity;
    private int inscriptions;
    private int presences;
    private float feedback;

    public DataActivityType() {
    }

    public DataActivityType(int quantity, int inscriptions, int presences, float feedback) {
        this.quantity = quantity;
        this.inscriptions = inscriptions;
        this.presences = presences;
        this.feedback = feedback;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getInscriptions() {
        return inscriptions;
    }

    public void setInscriptions(int inscriptions) {
        this.inscriptions = inscriptions;
    }

    public int getPresences() {
        return presences;
    }

    public void setPresences(int presences) {
        this.presences = presences;
    }

    public float getFeedback() {
        return feedback;
    }

    public void setFeedback(float feedback) {
        this.feedback = feedback;
    }
}
