package com.maciejp.postsapp.comment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    private final CommentDataAccessObject commentDataAccessObject;

    @Autowired
    public CommentService(CommentDataAccessObject commentDataAccessObject) {
        this.commentDataAccessObject = commentDataAccessObject;
    }
}
