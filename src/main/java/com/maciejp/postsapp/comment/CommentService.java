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
        if (c.getText() == "") {
            throw new CommentCreationException("Comment text cannot be empty");
        }
        commentDataAccessObject.addComment(c);
    }

    public void updateCommentPoints(String pointsJSON, String author) throws UpdatePointsException {
        long commentId;
        int value;

        try {
            JSONObject pointsJSONObject = new JSONObject(pointsJSON);
            commentId = pointsJSONObject.getLong("commentId");
            value = pointsJSONObject.getInt("value");
        } catch (JSONException e) {
            throw new UpdatePointsException("Cannot update points");
        }

        if (value != 1 && value != -1) {
            throw new UpdatePointsException("Invalid points value");
        }

        commentDataAccessObject.updatePoints(author, commentId, value);
    }

    public int getCommentScore(long id) {
        return commentDataAccessObject.selectCommentScore(id);
    }

    private Comment parseCommentJSON(String commentJSON, String author) {
        Long parentCommentId;
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
            parentCommentId = null;
        }

        return new Comment(0, author, parentId, 0, parentCommentId, text, null);
    }
}
