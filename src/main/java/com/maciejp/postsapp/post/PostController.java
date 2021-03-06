package com.maciejp.postsapp.post;

import com.maciejp.postsapp.exception.PostCreationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public List<Post> getAllPosts() {
        return postService.getAllPosts();
    }

    @GetMapping("/{id}")
    public Post getPostById(@PathVariable("id") long id) {
        return postService.getPostById(id);
    }

    @PatchMapping("/visit/{id}")
    public void addVisit(@PathVariable("id") long id) {
        postService.addVisit(id);
    }


    @GetMapping("/head")
    public List<Post> getAllPostHeads() {
        return postService.getAllPostsHeads();
    }


    @PostMapping
    public String addPost(@RequestBody String post) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            String userName = authentication.getName();
            try {
                postService.addPostAsString(post, userName);
                return "{\"valid\" : true}";
            } catch (PostCreationException e) {
                return "{\"valid\" : false, \"message\" : \"" + e.getMessage() + "\"}";
            }
        } else {
            return "{\"valid\" : false, \"message\" : \"please log in\"}";
        }
    }

}
