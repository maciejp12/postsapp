package com.maciejp.postsapp.post;

import org.junit.*;


public class PostServiceTest {

    private static PostService postService;
    private static String testTitle = "test_title";

    @BeforeClass
    public static void setup() {
        postService = new PostService(new PostDataAccessObject());
    }

    @AfterClass
    public static void clean() {
        postService.deletePostByTitle(testTitle);
    }

    @Test
    public void testAddPost() {
        Post savedPost = new Post();

        String title = testTitle;
        String author = "test_author";
        String content = "test_content";

        savedPost.setTitle(title);
        savedPost.setAuthor(author);
        savedPost.setContent(content);

        postService.addPost(savedPost);

        Post existingPost = postService.getPostByTitle(title);

        Assert.assertEquals(existingPost.getTitle(), title);
        Assert.assertEquals(existingPost.getAuthor(), author);
        Assert.assertEquals(existingPost.getContent(), content);
    }
}
