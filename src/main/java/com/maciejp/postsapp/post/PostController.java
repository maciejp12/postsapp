package com.maciejp.postsapp.post;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.maciejp.postsapp.expection.PostCreationException;
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
