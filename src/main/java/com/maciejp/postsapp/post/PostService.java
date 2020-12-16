package com.maciejp.postsapp.post;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    private final PostDataAccessObject postDataAccessObject;

    @Autowired
    public PostService(PostDataAccessObject postDataAccessObject) {
        this.postDataAccessObject = postDataAccessObject;
    }

    public List<Post> getAllPosts() {
        return postDataAccessObject.selectAllPosts();
    }

    public Post getPostByTitle(String title) {
        return postDataAccessObject.selectPostByTitle(title);
    }

    public void deletePostByTitle(String title) {
        postDataAccessObject.deletePostByTitle(title);
    }

    public void addPostAsString(String post) {
        addPost(parsePostJSON(post));
    }

    public void addPost(Post post) {
        postDataAccessObject.addPost(post);
    }

    private Post parsePostJSON(String postJSON) {
        try {
            JSONObject postJSONObject = new JSONObject(postJSON);
            Post post = new Post(0, postJSONObject.getString("title"),
                                    postJSONObject.getString("author"),
                                    postJSONObject.getString("content"),
                                    null);
            return post;
        } catch (JSONException e) {
            return null;
        }
    }
}
