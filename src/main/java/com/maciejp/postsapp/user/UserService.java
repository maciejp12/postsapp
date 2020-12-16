package com.maciejp.postsapp.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserDataAccessObject userDataAccessObject;

    @Autowired
    public UserService(UserDataAccessObject userDataAccessObject) {
        this.userDataAccessObject = userDataAccessObject;
    }

    public void addUser(User user) {
        userDataAccessObject.addUser(user);
    }

    public User getUserByName(String name) {
        return userDataAccessObject.selectUserByName(name);
    }

    public void deleteUserByName(String name) {
        userDataAccessObject.deleteUserByName(name);
    }

}

