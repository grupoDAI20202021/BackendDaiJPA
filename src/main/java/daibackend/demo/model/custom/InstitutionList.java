package daibackend.demo.model.custom;

public class InstitutionList {
    private String email;
    private String name;
    private String address;
    private String townHallName;
    private long idInstitution;

    public InstitutionList() {
    }

    public InstitutionList(String email, String name, String address, String townHallName, long idInstitution) {
        this.email = email;
        this.name = name;
        this.address = address;
        this.townHallName = townHallName;
        this.idInstitution = idInstitution;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public long getIdInstitution() {
        return idInstitution;
    }

    public void setIdInstitution(long idInstitution) {
        this.idInstitution = idInstitution;
    }

    public String getTownHallName() {
        return townHallName;
    }

    public void setTownHallName(String townHallName) {
        this.townHallName = townHallName;
    }
}
