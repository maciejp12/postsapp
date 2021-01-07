package com.maciejp.postsapp.comment;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Timestamp;

public class Comment {

    private long commentId;

    private String author;

    private long parentId;

    private int points;

    private Long parentCommentId;

    private String text;

    private Timestamp creationDate;

    public Comment(@JsonProperty("commentId") long commentId,
                   @JsonProperty("author") String author,
                   @JsonProperty("parentId") long parentId,
                   @JsonProperty("points") int points,
                   @JsonProperty("parentCommentId") Long parentCommentId,
                   @JsonProperty("text") String text,
                   @JsonProperty("creationDate") Timestamp creationDate) {
        this.commentId = commentId;
        this.author = author;
        this.parentId = parentId;
        this.points = points;
        this.parentCommentId = parentCommentId;
        this.text = text;
        this.creationDate = creationDate;
    }

    public long getCommentId() {
        return commentId;
    }

    public void setCommentId(long commentId) {
        this.commentId = commentId;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public long getParentId() {
        return parentId;
    }

    public void setParentId(long parentId) {
        this.parentId = parentId;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public Long getParentCommentId() {
        return parentCommentId;
    }

    public void setParentCommentId(Long parentCommentId) {
        this.parentCommentId = parentCommentId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "commentId=" + commentId +
                ", author='" + author + '\'' +
                ", parentId=" + parentId +
                ", points=" + points +
                ", parentCommentId=" + parentCommentId +
                ", text='" + text + '\'' +
                ", creationDate=" + creationDate +
                '}';
    }
}
