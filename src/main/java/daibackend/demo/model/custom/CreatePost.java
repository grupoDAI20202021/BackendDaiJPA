package daibackend.demo.model.custom;

public class CreatePost {
    private String post;

    private long idChild;

    public CreatePost() {
    }

    public CreatePost(String post, long idChild) {
        this.post = post;
        this.idChild = idChild;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public long getIdChild() {
        return idChild;
    }

    public void setIdChild(long idChild) {
        this.idChild = idChild;
    }
}
