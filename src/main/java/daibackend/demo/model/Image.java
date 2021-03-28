package daibackend.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity(name = "image")
@Table(name = "image")

public class Image {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long idImage;

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private String type;

    @Column(name = "picByte")
    @Lob
    private byte[] picByte;


    public Image() {
        super();
    }

    public Image(Long idImage, String name, String type, byte[] picByte) {
        this.idImage = idImage;
        this.name = name;
        this.type = type;
        this.picByte = picByte;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public byte[] getPicByte() {
        return picByte;
    }

    public void setPicByte(byte[] picByte) {
        this.picByte = picByte;
    }

    public Long getId() {
        return idImage;
    }

    public void setId(Long id_image) {
        this.idImage = id_image;
    }


}
