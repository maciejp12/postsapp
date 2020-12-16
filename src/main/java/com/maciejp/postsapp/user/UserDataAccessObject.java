package com.maciejp.postsapp.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;

@Repository
public class UserDataAccessObject {

    private static final String dbUserName = "posts_user";
    private static final String dbUserPassword = "postspasswd";
    private static final String dbName = "postsapp";
    private static final String host = "jdbc:mysql://localhost:3306/";
    private Connection connection = null;
    private PreparedStatement statement = null;

    @Autowired
    public UserDataAccessObject() {
    }


}
