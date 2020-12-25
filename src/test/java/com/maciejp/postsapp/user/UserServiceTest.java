package com.maciejp.postsapp.user;

import com.maciejp.postsapp.exception.UserRegisterException;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.fail;

public class UserServiceTest {

    private static UserService userService;

    private static String testName = "test_add_user_name";

    private static String testGetByNameName = "test_get_user_by_name_name";
    private static String testGetByNameEmail = "test_get_user_by_name_email@example.com";

    private static String testGetByEmailEmail = "test_get_user_by_email_email@example.com";
    private static String testGetByEmailName = "test_get_user_by_email_name";

    private static String testDeleteByNameName = "test_delete_user_by_name_name";
    private static String testDeleteByNameEmail = "test_delete_user_by_name_email@example.com";

    @BeforeClass
    public static void setup() {
        userService = new UserService(new UserDataAccessObject());

        User testGetByNameUser = new User();
        User testGetByEmailUser = new User();
        User testDeleteByNameUser = new User();

        String password = "test_password";

        testGetByNameUser.setName(testGetByNameName);
        testGetByNameUser.setEmail(testGetByNameEmail);
        testGetByNameUser.setPassword(password);

        testGetByEmailUser.setName(testGetByEmailName);
        testGetByEmailUser.setEmail(testGetByEmailEmail);
        testGetByEmailUser.setPassword(password);

        testDeleteByNameUser.setName(testDeleteByNameName);
        testDeleteByNameUser.setEmail(testDeleteByNameEmail);
        testDeleteByNameUser.setPassword(password);

        try {
            userService.addUser(testGetByNameUser);
            userService.addUser(testGetByEmailUser);
            userService.addUser(testDeleteByNameUser);
        } catch (UserRegisterException e) {
            System.out.println(e.getMessage());
            fail();
        }
    }

    @AfterClass
    public static void clean() {
        userService.deleteUserByName(testName);
        userService.deleteUserByName(testGetByNameName);
        userService.deleteUserByName(testGetByEmailName);
    }

    @Test
    public void testAddUser() {
        User savedUser = new User();

        String email = "test_add_user_email@example.com";
        String password = "test_add_user_password";

        savedUser.setName(testName);
        savedUser.setEmail(email);
        savedUser.setPassword(password);

        try {
            userService.addUser(savedUser);
        } catch (UserRegisterException e) {
            fail();
        }

        User existingUser = userService.getUserByName(testName);

        Assert.assertNotNull(existingUser);
        Assert.assertEquals(existingUser.getName(), testName);
        Assert.assertEquals(existingUser.getEmail(), email);

        // Try adding user with same name

        User userWithSameName = new User();

        String fakeEmail = "test_add_user_fake_email@example.com";

        userWithSameName.setName(testName);
        userWithSameName.setEmail(fakeEmail);
        userWithSameName.setPassword(password);

        String expectedMessage = "name already in use";

        try {
            userService.addUser(userWithSameName);
            fail("method didn't throw exception");
        } catch (UserRegisterException e) {
            Assert.assertEquals(e.getMessage(), expectedMessage);
        }

        // Try adding user with same email

        User userWithSameEmail = new User();

        String fakeName = "test_add_user_fake_name";

        userWithSameEmail.setName(fakeName);
        userWithSameEmail.setEmail(email);
        userWithSameEmail.setPassword(password);

        expectedMessage = "email already in use";

        try {
            userService.addUser(userWithSameEmail);
            fail("method didn't throw exception");
        } catch (UserRegisterException e) {
            Assert.assertEquals(e.getMessage(), expectedMessage);
        }
    }

    @Test
    public void testAddUserWithTooShortName() {
        User user = new User();

        user.setName("ab");
        user.setPassword("password");
        user.setEmail("test_email@example.com");

        String expectedMessage = "name must be between 3 and 64 characters long";

        try {
            userService.addUser(user);
            fail("method didn't throw exception");
        } catch (UserRegisterException e) {
            Assert.assertEquals(e.getMessage(), expectedMessage);
        }
    }

    @Test
    public void testAddUserWithTooLongName() {
        User user = new User();

        user.setName("qwertyuiqwertyuiqwertyuiqwertyuiqwertyuiqwertyuiqwertyuiqwertyuix");
        user.setPassword("password");
        user.setEmail("test_email@example.com");

        String expectedMessage = "name must be between 3 and 64 characters long";

        try {
            userService.addUser(user);
            fail("method didn't throw exception");
        } catch (UserRegisterException e) {
            Assert.assertEquals(e.getMessage(), expectedMessage);
        }
    }

    @Test
    public void testAddUserWithTooShortPassword() {
        User user = new User();

        user.setName("test_user_1");
        user.setPassword("passw");
        user.setEmail("test_email@example.com");

        String expectedMessage = "password must be between 6 and 64 characters long";

        try {
            userService.addUser(user);
            fail("method didn't throw exception");
        } catch (UserRegisterException e) {
            Assert.assertEquals(e.getMessage(), expectedMessage);
        }
    }

    @Test
    public void testAddUserWithTooLongPassword() {
        User user = new User();

        user.setName("test_user_1");
        user.setPassword("qwertyuiqwertyuiqwertyuiqwertyuiqwertyuiqwertyuiqwertyuiqwertyuix");
        user.setEmail("test_email@example.com");

        String expectedMessage = "password must be between 6 and 64 characters long";

        try {
            userService.addUser(user);
            fail("method didn't throw exception");
        } catch (UserRegisterException e) {
            Assert.assertEquals(e.getMessage(), expectedMessage);
        }
    }

    @Test
    public void testAddUserWithInvalidEmail() {
        User user = new User();

        user.setName("test_user_1");
        user.setPassword("password");
        user.setEmail("invalid_email@examplecom");

        String expectedMessage = "email is not valid";

        try {
            userService.addUser(user);
            fail("method didn't throw exception");
        } catch (UserRegisterException e) {
            Assert.assertEquals(e.getMessage(), expectedMessage);
        }
    }

    @Test
    public void testGetUserByName() {
        User existingUser = userService.getUserByName(testGetByNameName);

        Assert.assertNotNull(existingUser);
        Assert.assertEquals(existingUser.getName(), testGetByNameName);
        Assert.assertEquals(existingUser.getEmail(), testGetByNameEmail);
    }

    @Test
    public void testGetUserByEmail() {
        User existingUser = userService.getUserByEmail(testGetByEmailEmail);

        Assert.assertNotNull(existingUser);
        Assert.assertEquals(existingUser.getName(), testGetByEmailName);
        Assert.assertEquals(existingUser.getEmail(), testGetByEmailEmail);
    }

    @Test
    public void testDeleteUserByName() {
        User beforeDeleteUser = userService.getUserByName(testDeleteByNameName);

        Assert.assertNotNull(beforeDeleteUser);
        Assert.assertEquals(beforeDeleteUser.getName(), testDeleteByNameName);
        Assert.assertEquals(beforeDeleteUser.getEmail(), testDeleteByNameEmail);

        userService.deleteUserByName(testDeleteByNameName);

        User afterDeleteUser = userService.getUserByName(testDeleteByNameName);
        Assert.assertNull(afterDeleteUser);
    }
}

