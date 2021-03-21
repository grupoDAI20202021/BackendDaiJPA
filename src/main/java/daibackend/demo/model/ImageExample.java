package daibackend.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity(name = "Image")
@Table(name = "Image")

public class ImageExample {


    @Id
    @Column(name = "id_image")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id_image;

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private String type;

    @Column(name = "picByte")
    @Lob
    private byte[] picByte;


    public ImageExample() {
        super();
    }

    public ImageExample(Long id_image, String name, String type, byte[] picByte) {
        this.id_image = id_image;
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
        return id_image;
    }

    public void setId(Long id_image) {
        this.id_image = id_image;
    }


}
