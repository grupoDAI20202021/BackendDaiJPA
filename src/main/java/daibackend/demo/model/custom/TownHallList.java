package daibackend.demo.model.custom;

public class TownHallList {

    private String email;
    private String name;
    private String address;
    private long idTownHall;

    public TownHallList() {
    }
    
    public TownHallList(String email, String name, String address,long idTownHall) {
        this.email = email;
        this.name = name;
        this.address = address;
        this.idTownHall=idTownHall;
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

    public long getIdTownHall() {
        return idTownHall;
    }

    public void setIdTownHall(long idTownHall) {
        this.idTownHall = idTownHall;
    }
}
