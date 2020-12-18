package com.maciejp.postsapp.post;

import com.maciejp.postsapp.expection.PostCreationException;
import com.maciejp.postsapp.expection.UserRegisterException;
import com.maciejp.postsapp.user.User;
import com.maciejp.postsapp.user.UserDataAccessObject;
import com.maciejp.postsapp.user.UserService;
import org.junit.*;

import static org.junit.jupiter.api.Assertions.fail;


public class PostServiceTest {

    private static PostService postService;
    private static UserService userService;

    private static String testTitle = "test_title";

    private static String testAuthorName = "test_add_post_user";

    @BeforeClass
    public static void setup() {
        postService = new PostService(new PostDataAccessObject());
        userService = new UserService(new UserDataAccessObject());

        User user = new User();

        user.setName(testAuthorName);
        user.setEmail("test_author_email@example.com");
        user.setPassword("test_password");

        try {
            userService.addUser(user);
        } catch (UserRegisterException e) {
            fail("cannot create test user");
        }
    }

    @AfterClass
    public static void clean() {
        postService.deletePostByTitle(testTitle);
        userService.deleteUserByName(testAuthorName);
    }

    @Test
    public void testAddPost() {
        Post savedPost = new Post();

        String title = testTitle;
        String author = testAuthorName;
        String content = "test_content";

        savedPost.setTitle(title);
        savedPost.setAuthor(author);
        savedPost.setContent(content);

        try {
            postService.addPost(savedPost);
        } catch (PostCreationException e) {
            fail("cannot create new post");
        }

        String expectedUserName = userService.getUserByName(testAuthorName).getName();

        Post existingPost = postService.getPostByTitle(title);

        Assert.assertEquals(existingPost.getTitle(), title);
        Assert.assertEquals(existingPost.getAuthor(), expectedUserName);
        Assert.assertEquals(existingPost.getContent(), content);
    }
}
