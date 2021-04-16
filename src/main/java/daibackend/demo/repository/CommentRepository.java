package daibackend.demo.repository;

import daibackend.demo.model.Child;
import daibackend.demo.model.Comment;
import daibackend.demo.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllByPost(Post post);

    Comment findDistinctByIdComment(long idComment);

    @Transactional
    @Modifying
    @Query("UPDATE comment C SET C.comment= ?1  where C.idComment= ?2")
    void updateComment(String comment, long idComment);

}
