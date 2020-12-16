package com.maciejp.postsapp.post;

import com.fasterxml.jackson.databind.util.JSONPObject;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    private final PostDataAccessService postDataAccessService;

    @Autowired
    public PostService(PostDataAccessService postDataAccessService) {
        this.postDataAccessService = postDataAccessService;
    }


    public List<Post> getAllPosts() {
        return postDataAccessService.selectAllPosts();
    }

    public void addPost(String post) {
        postDataAccessService.addPost(parsePostJSON(post));
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
