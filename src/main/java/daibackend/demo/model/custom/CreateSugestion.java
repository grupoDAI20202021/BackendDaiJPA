package daibackend.demo.model.custom;

public class CreateSugestion {
    private long idChild;
    private String content;
    private int experience;

    public CreateSugestion() {
    }

    public CreateSugestion(long idChild, String content, int experience) {
        this.idChild = idChild;
        this.content = content;
        this.experience = experience;
    }

    public long getIdChild() {
        return idChild;
    }

    public void setIdChild(long idChild) {
        this.idChild = idChild;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }
}
