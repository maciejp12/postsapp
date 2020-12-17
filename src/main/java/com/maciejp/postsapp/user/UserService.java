package com.maciejp.postsapp.user;

import com.maciejp.postsapp.expection.UserRegisterException;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserDataAccessObject userDataAccessObject;

    @Autowired
    public UserService(UserDataAccessObject userDataAccessObject) {
        this.userDataAccessObject = userDataAccessObject;
    }

    public void addUser(User user) throws UserRegisterException {
        user.setPassword(encodePassword(user.getPassword()));
        if (userDataAccessObject.selectUserByName(user.getName()) != null) {
            throw new UserRegisterException("name already in use");
        }

        if (userDataAccessObject.selectUserByEmail(user.getEmail()) != null) {
            throw new UserRegisterException("email already in use");
        }
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

}

