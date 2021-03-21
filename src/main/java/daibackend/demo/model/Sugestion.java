package daibackend.demo.model;


import daibackend.demo.util.ConstantUtils;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Entity(name = "Sugestion")
@Table(name = "Sugestion")
public class Sugestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_sugestion;

    @ManyToOne
    @JoinColumn(name = "id_child", referencedColumnName = "id_child", nullable = false)
    private Child child;

    @NotBlank(message = "Can't be blank")
    @Pattern(regexp = ConstantUtils.CHAR_PATTERN, message = "Can only letters, letters with special characters and spaces")
    private String content;

    @ManyToOne
    @JoinColumn(name = "id_image", referencedColumnName = "id_image", nullable = false)
    private ImageExample imageExample;

    private byte voice;

    public Sugestion() {
    }

    public Sugestion(Long id_sugestion, Child child,  String content, ImageExample imageExample, byte voice) {
        this.id_sugestion = id_sugestion;
        this.child = child;
        this.content = content;
        this.imageExample = imageExample;
        this.voice = voice;
    }

    public Long getId_sugestion() {
        return id_sugestion;
    }

    public void setId_sugestion(Long id_sugestion) {
        this.id_sugestion = id_sugestion;
    }

    public Child getChild() {
        return child;
    }

    public void setChild(Child child) {
        this.child = child;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ImageExample getImageExample() {
        return imageExample;
    }

    public void setImageExample(ImageExample imageExample) {
        this.imageExample = imageExample;
    }

    public byte getVoice() {
        return voice;
    }

    public void setVoice(byte voice) {
        this.voice = voice;
    }
}
