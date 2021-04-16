package daibackend.demo.controller;


import daibackend.demo.model.Activity;
import daibackend.demo.model.Child;
import daibackend.demo.model.Institution;
import daibackend.demo.model.Post;
import daibackend.demo.model.custom.CreatePost;
import daibackend.demo.model.custom.updateActivityTownHall;
import daibackend.demo.payload.response.ApiResponse;
import daibackend.demo.repository.ChildRepository;
import daibackend.demo.repository.InstitutionRepository;
import daibackend.demo.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    //@PreAuthorize("hasRole('GUARD') or hasRole('MANAGER') or hasRole('NETWORKMAN')")
    @GetMapping("/posts")
    public List<Post> listPosts(/*@CurrentUser UserPrincipal currentUser*/) {
        //User userLogged = userRepository.findByUserId(currentUser.getId());
        //Set<Role> roleUserLogged = userLogged.getRoles();

        // Get Permissions
        /*if (String.valueOf(roleUserLogged).equals("[Role [id=0]]")
                || String.valueOf(roleUserLogged).equals("[Role [id=1]]")) {
            return alertLogRepository.findAlertLogsByPrison(userLogged.getPrison());
        }*/
        return postRepository.findAll();
    }

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
                return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Activity created", newPost.getIdPost()),
                        HttpStatus.CREATED);

        } catch (Exception e) {
            return new ResponseEntity<ApiResponse>(new ApiResponse(false, "Invalid data format"),
                    HttpStatus.BAD_REQUEST);
        }
    }

    //@PreAuthorize("hasRole('GUARD') or hasRole('MANAGER') or hasRole('NETWORKMAN')")  // Se calhar tirar
    @PutMapping("/posts/{idPost}")
    public ResponseEntity<ApiResponse> updatePost(@PathVariable(value="idPost")long idPost, @RequestBody updateActivityTownHall update) {
        try {
            if (postRepository.findDistinctByIdPost(idPost).equals(null)) {
                return new ResponseEntity<ApiResponse>(new ApiResponse(false, "Invalid data format"),
                        HttpStatus.BAD_REQUEST);
            }

            return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Post updated.", idPost),
                    HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @DeleteMapping("/posts/{idPost}")
    public ResponseEntity<ApiResponse> deletePost(@PathVariable (value="idPost")long idPost) {
        try {
            Post post = postRepository.findDistinctByIdPost(idPost);
            postRepository.deleteDistinctByIdPost(idPost);
            return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Post deleted.", idPost),
                    HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<ApiResponse>(new ApiResponse(false, "Invalid data format"),
                    HttpStatus.BAD_REQUEST);
        }
    }
}
