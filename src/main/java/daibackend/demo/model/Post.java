package daibackend.demo.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import daibackend.demo.util.ConstantUtils;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;

@Entity(name = "post")
@Table(name = "post")
public class Post {
    @Id
    private Long idPost;

    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @NotNull(message = "Can't be null")
    private Date insert_data;

    @ManyToOne
    @JoinColumn(name = "idChild", referencedColumnName = "idChild", nullable = false)
    private Child child;

    @NotBlank(message = "Can't be blank")
    @Pattern(regexp = ConstantUtils.DESCRIPTION_PATTERN, message = "Can only letters, letters with special characters and spaces")
    private String post;


    public Post() {
    }

    public Post(Long idPost, @NotNull(message = "Can't be null") Date insert_data, Child child, @NotBlank(message = "Can't be blank") @Pattern(regexp = ConstantUtils.DESCRIPTION_PATTERN, message = "Can only letters, letters with special characters and spaces") String post) {
        this.idPost = idPost;
        this.insert_data = insert_data;
        this.child = child;
        this.post = post;
    }

    public Date getInsert_data() {
        return insert_data;
    }

    public void setInsert_data(Date insert_data) {
        this.insert_data = insert_data;
    }

    public Child getChild() {
        return child;
    }

    public void setChild(Child child) {
        this.child = child;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public void setIdPost(Long idPost) {
        this.idPost = idPost;
    }

    public Long getIdPost() {
        return idPost;
    }
}
