package daibackend.demo.repository;

import daibackend.demo.model.ActivityType;
import daibackend.demo.model.Child;
import daibackend.demo.model.Login;
import daibackend.demo.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface PostRepository  extends JpaRepository<Post, Long> {

    Post findDistinctByIdPost(long idPost);

    @Override
    List<Post> findAll();


    List<Post> findAllByChild(Child child);

    @Transactional
    @Modifying
    @Query("UPDATE post P SET P.post= ?1  where P.idPost= ?2")
    void updatePost(String post, long idPost);

    void deleteDistinctByIdPost(long idPost);
}
