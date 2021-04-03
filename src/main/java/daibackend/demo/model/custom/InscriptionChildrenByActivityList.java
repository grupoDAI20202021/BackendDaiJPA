package daibackend.demo.model.custom;

import javax.persistence.Id;

public class InscriptionChildrenByActivityList {

    @Id
    private long idChild;
    private String name;
    private int age;

    public InscriptionChildrenByActivityList() {
    }

    public InscriptionChildrenByActivityList(long idChild, String name, int age) {
        this.idChild = idChild;
        this.name = name;
        this.age = age;
    }

    public long getIdChild() {
        return idChild;
    }

    public void setIdChild(long idChild) {
        this.idChild = idChild;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
