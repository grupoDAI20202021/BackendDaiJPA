package daibackend.demo.controller;

import daibackend.demo.model.Child;
import daibackend.demo.model.Comment;
import daibackend.demo.model.Post;
import daibackend.demo.model.custom.CreatePost;
import daibackend.demo.model.custom.InstitutionList;
import daibackend.demo.model.custom.updateActivityTownHall;
import daibackend.demo.model.custom.updateEmail;
import daibackend.demo.payload.response.ApiResponse;
import daibackend.demo.repository.ChildRepository;
import daibackend.demo.repository.CommentRepository;
import daibackend.demo.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class CommentController {

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    ChildRepository childRepository;

    //@PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('TOWNHALL') or hasRole('INSTITUTION') or hasROLE('CHILD')")
    @GetMapping("/posts/{idPost}/comments")
    public List<Comment> listComments(/*@CurrentUser UserPrincipal currentUser*/@PathVariable long idPost) {

        Post post = postRepository.findDistinctByIdPost(idPost);
        return commentRepository.findAllByPost(post);
    }

   // @PreAuthorize("hasRole('ADMINISTRATOR') or hasROLE('CHILD')")
    @PostMapping("/posts/{idPost}/comments")
    public ResponseEntity<ApiResponse> saveComment(@PathVariable long idPost ,@RequestBody CreatePost comment) {
        try {
            // Activity Attributes
            Post post= postRepository.findDistinctByIdPost(idPost);
            Long idChild = comment.getIdChild();
            Date insert_date = new Date();
            String content= comment.getPost();
            Child child = childRepository.findDistinctByIdChild(idChild);
            Comment newComment = new Comment(null,insert_date,child,post,content);
            commentRepository.save(newComment);
            return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Comment created", newComment.getIdComment()),
                    HttpStatus.CREATED);

        } catch (Exception e) {
            return new ResponseEntity<ApiResponse>(new ApiResponse(false, "Invalid data format"),
                    HttpStatus.BAD_REQUEST);
        }
    }

   // @PreAuthorize("hasROLE('CHILD')")
    @PutMapping("/comments/{idComment}")
    public ResponseEntity<ApiResponse> updateComment(@PathVariable(value="idComment")long idComment, @RequestBody updateEmail update) {
        try {
            if (commentRepository.findDistinctByIdComment(idComment).equals(null)) {
                return new ResponseEntity<ApiResponse>(new ApiResponse(false, "Invalid data format"),
                        HttpStatus.BAD_REQUEST);
            }

            commentRepository.updateComment(update.getEmail(),idComment);
            return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Comment updated.", idComment),
                    HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

   // @PreAuthorize("hasRole('ADMINISTRATOR') or hasROLE('CHILD')")
    @DeleteMapping("/comments/{idComment}")
    public ResponseEntity<ApiResponse> deleteComment(@PathVariable (value="idComment")long idComment) {
        try {
            Comment comment = commentRepository.findDistinctByIdComment(idComment);
            commentRepository.delete(comment);
            return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Comment deleted.", idComment),
                    HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<ApiResponse>(new ApiResponse(false, "Invalid data format"),
                    HttpStatus.BAD_REQUEST);
        }
    }
}
