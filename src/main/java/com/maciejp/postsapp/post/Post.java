package com.maciejp.postsapp.post;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Timestamp;


public class Post {

    private long postId;

    private String title;

    private String author;

    private String content;

    private Timestamp creationDate;

    public Post() {

    }

    public Post(@JsonProperty("postId") long postId,
                @JsonProperty("title") String title,
                @JsonProperty("author") String author,
                @JsonProperty("content") String content,
                @JsonProperty("creationDate") Timestamp creationDate) {
        this.postId = postId;
        this.title = title;
        this.author = author;
        this.content = content;
        this.creationDate = creationDate;
    }

    public long getPostId() {
        return postId;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setPostId(long postId) {
        this.postId = postId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public String toString() {
        return "Post{" +
                "postId=" + postId +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", content='" + content + '\'' +
                ", creationDate=" + creationDate +
                '}';
    }
}
