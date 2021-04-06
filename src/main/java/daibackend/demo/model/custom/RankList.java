package daibackend.demo.model.custom;

public class RankList {
    private long  idChild;
    private String name;
    private int age;
    private long points;

    public RankList() {

    }

    public RankList(long idChild, String name, int age, long points) {
        this.idChild = idChild;
        this.name = name;
        this.age = age;
        this.points = points;
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

    public long getPoints() {
        return points;
    }

    public void setPoints(long points) {
        this.points = points;
    }
}
