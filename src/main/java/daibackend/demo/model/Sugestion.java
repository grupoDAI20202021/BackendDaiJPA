package daibackend.demo.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import daibackend.demo.util.ConstantUtils;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;

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
    @Pattern(regexp = ConstantUtils.DESCRIPTION_PATTERN, message = "Allow only letters, letters with special characters, numbers and spaces")
    private String content;

    private byte voice;

    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @NotNull(message = "Can't be null")
    private Date init_data;

    private int checked;

    private int experience;


    public Sugestion() {
    }

    public Sugestion(Long idSugestion, Child child, String content,  byte voice, Date init_data,int checked,int experience) {
        this.idSugestion = idSugestion;
        this.child = child;
        this.content = content;
        this.voice = voice;
        this.init_data=init_data;
        this.checked=checked;
        this.experience=experience;
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

    public Date getInit_data() {
        return init_data;
    }

    public void setInit_data(Date init_data) {
        this.init_data = init_data;
    }

    public int getChecked() {
        return checked;
    }

    public void setChecked(int checked) {
        this.checked = checked;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }
}
