package daibackend.demo.model.custom;

public class CreateSponsor {
    private String name;

    public CreateSponsor() {
    }

    public CreateSponsor(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
