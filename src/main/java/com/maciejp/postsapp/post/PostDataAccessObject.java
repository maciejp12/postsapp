package com.maciejp.postsapp.post;

import com.maciejp.postsapp.expection.PostCreationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;

import java.util.ArrayList;
import java.util.List;

@Repository
public class PostDataAccessObject {

    private static final String dbUserName = "posts_user";
    private static final String dbUserPassword = "postspasswd";
    private static final String dbName = "postsapp";
    private static final String host = "jdbc:mysql://localhost:3306/";
    private Connection connection = null;
    private PreparedStatement statement = null;

    @Autowired
    public PostDataAccessObject() {
    }

    public List<Post> selectAllPosts() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            List<Post> result = new ArrayList<>();

            String sql = "SELECT post_id, title, author, content, creation_date FROM posts " +
                    "JOIN users ON posts.author = users.user_id ORDER BY " +
                    "creation_date DESC";

            connection = DriverManager.getConnection(host + dbName + "?useSSL=false&allowPublicKeyRetrieval=true",
                    dbUserName, dbUserPassword);
            statement = connection.prepareStatement(sql);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                result.add(new Post(resultSet.getLong(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getTimestamp(5)));
            }

            return result;
        } catch (ClassNotFoundException | SQLException e) {
            return new ArrayList<Post>();
        } finally {
            close();
        }
    }

    public Post selectPostByTitle(String title) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Post result = null;

            String sql = "SELECT post_id, title, name, content, creation_date FROM posts " +
                    "JOIN users ON posts.author = users.user_id WHERE title = ? ";

            connection = DriverManager.getConnection(host + dbName + "?useSSL=false&allowPublicKeyRetrieval=true",
                    dbUserName, dbUserPassword);
            statement = connection.prepareStatement(sql);
            statement.setString(1, title);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                result = new Post(resultSet.getLong(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getTimestamp(5));
            }

            return result;
        } catch (ClassNotFoundException | SQLException e) {
            return null;
        } finally {
            close();
        }
    }

    public void addPost(Post post) throws PostCreationException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            connection = DriverManager.getConnection(host + dbName + "?useSSL=false&allowPublicKeyRetrieval=true",
                    dbUserName, dbUserPassword);

            long authorId = 0;

            String selectAuthorSql = "SELECT user_id FROM users WHERE name = ? ";

            statement = connection.prepareStatement(selectAuthorSql);
            statement.setString(1, post.getAuthor());

            ResultSet resultSet = statement.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                throw new PostCreationException("Please log in to create new post");
            }

            while (resultSet.next()) {
                authorId = resultSet.getLong(1);
            }

            String sql = "INSERT INTO posts(title, author, content, creation_date) " +
                    "VALUES( ? ,  ? ,  ? , now())";


            statement = connection.prepareStatement(sql);

            statement.setString(1, post.getTitle());
            statement.setLong(2, authorId);
            statement.setString(3, post.getContent());

            statement.executeUpdate();

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            close();
        }
    }

    public void deletePostByTitle(String title) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            String sql = "DELETE FROM posts WHERE title = ? ";

            connection = DriverManager.getConnection(host + dbName + "?useSSL=false&allowPublicKeyRetrieval=true",
                    dbUserName, dbUserPassword);
            statement = connection.prepareStatement(sql);
            statement.setString(1, title);
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
