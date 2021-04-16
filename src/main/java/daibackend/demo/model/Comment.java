package daibackend.demo.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import daibackend.demo.util.ConstantUtils;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;

@Entity(name = "comment")
@Table(name = "comment")
public class Comment {

    @Id
    private Long idComment;

    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @NotNull(message = "Can't be null")
    private Date insert_data;

    @ManyToOne
    @JoinColumn(name = "idChild", referencedColumnName = "idChild", nullable = false)
    private Child child;

    @ManyToOne
    @JoinColumn(name = "idPost", referencedColumnName = "idPost", nullable = false)
    private Post post;

    @NotBlank(message = "Can't be blank")
    @Pattern(regexp = ConstantUtils.DESCRIPTION_PATTERN, message = "Can only letters, letters with special characters and spaces")
    private String comment;

    public Comment() {
    }

    public Comment(Long idComment, @NotNull(message = "Can't be null") Date insert_data, Child child, Post post, @NotBlank(message = "Can't be blank") @Pattern(regexp = ConstantUtils.DESCRIPTION_PATTERN, message = "Can only letters, letters with special characters and spaces") String comment) {
        this.idComment = idComment;
        this.insert_data = insert_data;
        this.child = child;
        this.post = post;
        this.comment = comment;
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

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setIdComment(Long idComment) {
        this.idComment = idComment;
    }


    public Long getIdComment() {
        return idComment;
    }
}
