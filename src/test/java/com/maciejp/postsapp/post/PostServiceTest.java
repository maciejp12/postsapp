package com.maciejp.postsapp.post;

import com.maciejp.postsapp.expection.PostCreationException;
import com.maciejp.postsapp.expection.UserRegisterException;
import com.maciejp.postsapp.user.User;
import com.maciejp.postsapp.user.UserDataAccessObject;
import com.maciejp.postsapp.user.UserService;
import org.junit.*;
import org.springframework.test.context.TestExecutionListeners;

import static org.junit.jupiter.api.Assertions.fail;


public class PostServiceTest {

    private static PostService postService;
    private static UserService userService;

    private static String testTitle = "test_title";

    private static String testAuthorName = "test_add_post_user";
    private static String testNonExistingAuthorName = "test_non_existing_author_user";

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

        // confirm that user with this name doesn't exist
        userService.deleteUserByName(testNonExistingAuthorName);
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

    @Test
    public void testAddPostWithNonExistingUser() {
        Post savedPost = new Post();

        String title = "test_title_2";
        String author = testNonExistingAuthorName;
        String content = "test_content_2";

        savedPost.setTitle(title);
        savedPost.setAuthor(author);
        savedPost.setContent(content);

        String expectedMessage = "Please log in to create new post";

        try {
            postService.addPost(savedPost);
            fail("method didn't throw exception");
        } catch (PostCreationException e) {
            Assert.assertEquals(e.getMessage(), expectedMessage);
        }
    }
}
