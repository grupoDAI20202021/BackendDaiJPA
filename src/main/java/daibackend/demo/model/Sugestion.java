package daibackend.demo.model;


import daibackend.demo.util.ConstantUtils;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Entity(name = "sugestion")
@Table(name = "sugestion")
public class Sugestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idSugestion;

    @ManyToOne
    @JoinColumn(name = "idChild", referencedColumnName = "idChild", nullable = false)
    private Child child;

    @NotBlank(message = "Can't be blank")
    @Pattern(regexp = ConstantUtils.CHAR_PATTERN, message = "Can only letters, letters with special characters and spaces")
    private String content;

    private byte voice;

    public Sugestion() {
    }

    public Sugestion(Long idSugestion, Child child, String content,  byte voice) {
        this.idSugestion = idSugestion;
        this.child = child;
        this.content = content;
        this.voice = voice;
    }

    public Long getIdSugestion() {
        return idSugestion;
    }

    public void setIdSugestion(Long id_sugestion) {
        this.idSugestion = id_sugestion;
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

    public byte getVoice() {
        return voice;
    }

    public void setVoice(byte voice) {
        this.voice = voice;
    }
}
