package com.maciejp.postsapp.comment;

import com.maciejp.postsapp.exception.CommentCreationException;
import com.maciejp.postsapp.exception.UpdatePointsException;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    private final CommentDataAccessObject commentDataAccessObject;

    @Autowired
    public CommentService(CommentDataAccessObject commentDataAccessObject) {
        this.commentDataAccessObject = commentDataAccessObject;
    }

    public List<Comment> getAllCommentsOfPost(long id) {
        return commentDataAccessObject.selectAllCommentsOfPost(id);
    }

    public void addCommentAsString(String comment, String userName) throws CommentCreationException {
        addComment(parseCommentJSON(comment, userName));
    }

    public void addComment(Comment c) throws CommentCreationException {
        commentDataAccessObject.addComment(c);
    }


    public void decrementCommentPoints(String userName, long id) throws UpdatePointsException {
        commentDataAccessObject.updatePoints(userName, id, -1);
    }

    public void incrementCommentPoints(String userName, long id) throws UpdatePointsException {
        commentDataAccessObject.updatePoints(userName, id, 1);
    }

    private Comment parseCommentJSON(String commentJSON, String author) {
        long parentCommentId;
        long parentId;
        String text;

        try {
            JSONObject commentJSONObject = new JSONObject(commentJSON);
            parentId = commentJSONObject.getLong("parentId");
            text = commentJSONObject.getString("text");
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

        try {
            JSONObject commentJSONObject = new JSONObject(commentJSON);
            parentCommentId = commentJSONObject.getLong("parentCommentId");
        } catch (JSONException e) {
            parentCommentId = -1;
        }

        return new Comment(0, author, parentId, 0, parentCommentId, text, null);
    }
}
