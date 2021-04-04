package daibackend.demo.model.custom;

import javax.persistence.Id;

public class CreateInscription {
    @Id
    private long idChild;

    public CreateInscription() {
    }

    public CreateInscription(long idChild) {
        this.idChild = idChild;
    }

    public long getIdChild() {
        return idChild;
    }

    public void setIdChild(long idChild) {
        this.idChild = idChild;
    }
}
