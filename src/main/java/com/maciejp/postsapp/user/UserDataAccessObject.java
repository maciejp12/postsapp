package com.maciejp.postsapp.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;

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

    public User selectUserByName(String name) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            User result = null;

            String sql = "SELECT user_id, email, password, name FROM users WHERE name = ? ";

            connection = DriverManager.getConnection(host + dbName + "?useSSL=false", dbUserName, dbUserPassword);
            statement = connection.prepareStatement(sql);
            statement.setString(1, name);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                result = new User(resultSet.getLong(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4));
            }

            return result;
        } catch (ClassNotFoundException | SQLException e) {
            return null;
        } finally {
            close();
        }
    }

    public void addUser(User user) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            String sql = "INSERT INTO users(email, password, name) " +
                    "VALUES( ? ,  ? ,  ? )";


            connection = DriverManager.getConnection(host + dbName + "?useSSL=false", dbUserName, dbUserPassword);
            statement = connection.prepareStatement(sql);

            statement.setString(1, user.getEmail());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getName());

            statement.executeUpdate();

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            close();
        }
    }

    public void deleteUserByName(String name) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            String sql = "DELETE FROM users WHERE name = ? ";

            connection = DriverManager.getConnection(host + dbName + "?useSSL=false", dbUserName, dbUserPassword);
            statement = connection.prepareStatement(sql);
            statement.setString(1, name);
            statement.executeUpdate();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            close();
        }
    }

    private void close() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
