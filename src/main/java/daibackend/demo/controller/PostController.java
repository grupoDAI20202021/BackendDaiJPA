package daibackend.demo.controller;


import daibackend.demo.model.Activity;
import daibackend.demo.model.Child;
import daibackend.demo.model.Institution;
import daibackend.demo.model.Post;
import daibackend.demo.model.custom.CreatePost;
import daibackend.demo.model.custom.updateActivityTownHall;
import daibackend.demo.model.custom.updateEmail;
import daibackend.demo.payload.response.ApiResponse;
import daibackend.demo.repository.ChildRepository;
import daibackend.demo.repository.InstitutionRepository;
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
public class PostController {

    @Autowired
    PostRepository postRepository;

    @Autowired
    ChildRepository childRepository;

   // @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('TOWNHALL') or hasRole('INSTITUTION') or hasROLE('CHILD')")
    @GetMapping("/posts")
    public List<Post> listPosts(/*@CurrentUser UserPrincipal currentUser*/) {
        return postRepository.findAll();
    }

   // @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('TOWNHALL') or hasRole('INSTITUTION') or hasROLE('CHILD')")
    @GetMapping("/posts/{idPost}")
    public Post listPost(@PathVariable long idPost/*@CurrentUser UserPrincipal currentUser*/) {
        return postRepository.findDistinctByIdPost(idPost);
    }

    //@PreAuthorize("hasRole('ADMINISTRATOR') or hasROLE('CHILD')")
    @PostMapping("/posts")
    public ResponseEntity<ApiResponse> savePost(@RequestBody CreatePost post) {
        try {
            // Activity Attributes

            Long idChild = post.getIdChild();
            Date insert_date = new Date();
            String content= post.getPost();
            Child child = childRepository.findDistinctByIdChild(idChild);
            Post newPost = new Post(null,insert_date,child,content);
            postRepository.save(newPost);
                return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Post created", newPost.getIdPost()),
                        HttpStatus.CREATED);

        } catch (Exception e) {
            System.out.println(e);
            return new ResponseEntity<ApiResponse>(new ApiResponse(false, "Invalid data format"),
                    HttpStatus.BAD_REQUEST);
        }
    }

    //@PreAuthorize("hasRole('ADMINISTRATOR') or hasROLE('CHILD')")
    @PutMapping("/posts/{idPost}")
    public ResponseEntity<ApiResponse> updatePost(@PathVariable(value="idPost")long idPost, @RequestBody updateEmail update) {
        try {
            if (postRepository.findDistinctByIdPost(idPost).equals(null)) {
                return new ResponseEntity<ApiResponse>(new ApiResponse(false, "Invalid data format"),
                        HttpStatus.BAD_REQUEST);
            }
            postRepository.updatePost(update.getEmail(),idPost);
            return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Post updated.", idPost),
                    HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //@PreAuthorize("hasRole('ADMINISTRATOR') or hasROLE('CHILD')")
    @DeleteMapping("/posts/{idPost}")
    public ResponseEntity<ApiResponse> deletePost(@PathVariable (value="idPost")long idPost) {
        try {
            postRepository.deleteDistinctByIdPost(idPost);
            return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Post deleted.", idPost),
                    HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<ApiResponse>(new ApiResponse(false, "Invalid data format"),
                    HttpStatus.BAD_REQUEST);
        }
    }
}
