package com.maciejp.postsapp.comment;

import com.maciejp.postsapp.exception.CommentCreationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/parent/{id}")
    public List<Comment> getAllCommentsOfPost(@PathVariable("id") long id) {
        return commentService.getAllCommentsOfPost(id);
    }

    @PostMapping
    public String addComment(@RequestBody String comment) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            String userName = authentication.getName();
            try {
                commentService.addCommentAsString(comment, userName);
                return "{\"valid\" : true}";
            } catch (CommentCreationException e) {
                return "{\"valid\" : false, \"message\" : \"" + e.getMessage() + "\"}";
            }
        } else {
            return "{\"valid\" : false, \"message\" : \"please log in\"}";
        }
    }
}
