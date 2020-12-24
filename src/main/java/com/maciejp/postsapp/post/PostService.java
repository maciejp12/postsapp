package com.maciejp.postsapp.post;

import com.maciejp.postsapp.expection.PostCreationException;
import com.maciejp.postsapp.user.User;
import com.maciejp.postsapp.user.UserService;
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

    public List<Post> getAllPostsHeads() {
        return postDataAccessObject.selectAllPostsHeads(64);
    }

    public Post getPostById(long id) {
        return postDataAccessObject.selectPostById(id);
    }

    public Post getPostByTitle(String title) {
        return postDataAccessObject.selectPostByTitle(title);
    }

    public void deletePostByTitle(String title) {
        postDataAccessObject.deletePostByTitle(title);
    }

    public void addPostAsString(String post, String userName) throws PostCreationException {
        addPost(parsePostJSON(post, userName));
    }

    public void addPost(Post post) throws PostCreationException {
        if (!validateTitle(post.getTitle())) {
            throw new PostCreationException("title must be between 3 and 64 characters long");
        }

        if (!validateContent(post.getContent())) {
            throw new PostCreationException("content must be between 3 and 4096 characters long");
        }

        postDataAccessObject.addPost(post);
    }

    private Post parsePostJSON(String postJSON, String author) {
        try {
            JSONObject postJSONObject = new JSONObject(postJSON);
            Post post = new Post(0, postJSONObject.getString("title"),
                                    author,
                                    postJSONObject.getString("content"),
                                    null);
            return post;
        } catch (JSONException e) {
            return null;
        }
    }

    private boolean validateTitle(String title) {
        int len = title.length();
        return len >= 3 && len <= 64;
    }

    private boolean validateContent(String content) {
        int len = content.length();
        return len >= 3 && len <= 4096;
    }
}
