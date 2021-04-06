package daibackend.demo.model.custom;

import java.util.Date;

public class SponsorList {
    private long idSponsor;
    private String name;
    private Date insert_data;
    private long idImage;

    public SponsorList() {
    }

    public SponsorList(long idSponsor, String name, Date insert_data, long idImage) {
        this.idSponsor = idSponsor;
        this.name = name;
        this.insert_data = insert_data;
        this.idImage = idImage;
    }

    public long getIdSponsor() {
        return idSponsor;
    }

    public void setIdSponsor(long idSponsor) {
        this.idSponsor = idSponsor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getInsert_data() {
        return insert_data;
    }

    public void setInsert_data(Date insert_data) {
        this.insert_data = insert_data;
    }

    public long getIdImage() {
        return idImage;
    }

    public void setIdImage(long idImage) {
        this.idImage = idImage;
    }

}
