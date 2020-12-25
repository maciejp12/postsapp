package com.maciejp.postsapp.user;

import com.maciejp.postsapp.exception.UserRegisterException;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserService {

    private final UserDataAccessObject userDataAccessObject;

    @Autowired
    public UserService(UserDataAccessObject userDataAccessObject) {
        this.userDataAccessObject = userDataAccessObject;
    }

    public void addUser(User user) throws UserRegisterException {
        if (userDataAccessObject.selectUserByName(user.getName()) != null) {
            throw new UserRegisterException("name already in use");
        }

        if (userDataAccessObject.selectUserByEmail(user.getEmail()) != null) {
            throw new UserRegisterException("email already in use");
        }

        if (!validateName(user.getName())) {
            throw new UserRegisterException("name must be between 3 and 64 characters long");
        }

        if (!validatePassword(user.getPassword())) {
            throw new UserRegisterException("password must be between 6 and 64 characters long");
        }

        if (!validateEmail(user.getEmail())) {
            throw new UserRegisterException("email is not valid");
        }

        user.setPassword(encodePassword(user.getPassword()));
        userDataAccessObject.addUser(user);
    }

    public void addUserAsString(String userJSON) throws UserRegisterException {
        User user = parseUserJSON(userJSON);
        if (user != null) {
            addUser(user);
        } else {
            throw new UserRegisterException("Registration error");
        }
    }

    public User getUserByName(String name) {
        return userDataAccessObject.selectUserByName(name);
    }

    public User getUserByEmail(String email) {
        return userDataAccessObject.selectUserByEmail(email);
    }

    public void deleteUserByName(String name) {
        userDataAccessObject.deleteUserByName(name);
    }

    private User parseUserJSON(String userJSON) {
        try {
            JSONObject userJSONObject = new JSONObject(userJSON);
            return new User(0, userJSONObject.getString("email"),
                    userJSONObject.getString("password"),
                    userJSONObject.getString("name"));
        } catch (JSONException e) {
            return null;
        }
    }

    private String encodePassword(String password) {
        return new BCryptPasswordEncoder().encode(password);
    }

    private boolean validateName(String name) {
        int len = name.length();
        return len >= 3 && len <= 64;
    }

    private boolean validatePassword(String password) {
        int len = password.length();
        return len >= 6 && len <= 64;
    }

    private boolean validateEmail(String email) {
        String pattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}" +
                "\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(email);
        return m.matches();
    }

}

