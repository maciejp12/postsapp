package com.maciejp.postsapp.user;

import com.maciejp.postsapp.expection.UserRegisterException;
import com.maciejp.postsapp.user.UserService;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

public class UserServiceTest {

    private static UserService userService;
    private static String testName = "test_name";

    @BeforeClass
    public static void setup() {
        userService = new UserService(new UserDataAccessObject());
    }

    @AfterClass
    public static void clean() {
        userService.deleteUserByName(testName);
    }

    @Test
    public void testAddPost() {
        User savedUser = new User();

        String name = testName;
        String email = "test_author";
        String password = "test_content";

        savedUser.setName(name);
        savedUser.setEmail(email);
        savedUser.setPassword(password);

        try {
            userService.addUser(savedUser);
        } catch (UserRegisterException e) {
            fail();
        }

        User existingPost = userService.getUserByName(testName);

        Assert.assertEquals(existingPost.getName(), name);
        Assert.assertEquals(existingPost.getEmail(), email);
        Assert.assertEquals(existingPost.getPassword(), password);
    }
}
