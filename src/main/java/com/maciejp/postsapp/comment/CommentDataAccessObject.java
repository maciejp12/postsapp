package com.maciejp.postsapp.comment;

import com.maciejp.postsapp.exception.CommentCreationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CommentDataAccessObject {

    private static final String dbUserName = "posts_user";
    private static final String dbUserPassword = "postspasswd";
    private static final String dbName = "postsapp";
    private static final String host = "jdbc:mysql://localhost:3306/";
    private Connection connection = null;
    private PreparedStatement statement = null;

    @Autowired
    public CommentDataAccessObject() {
    }

    public List<Comment> selectAllCommentsOfPost(long id) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            List<Comment> result = new ArrayList<>();

            String sql = "SELECT comment_id, u.name, parent, points, parent_comment, comment_text, creation_date " +
                    "FROM comments JOIN users AS u ON comments.author = u.user_id " +
                    "WHERE parent = ? " +
                    "ORDER BY points, creation_date DESC";

            connection = DriverManager.getConnection(host + dbName + "?useSSL=false&allowPublicKeyRetrieval=true",
                    dbUserName, dbUserPassword);
            statement = connection.prepareStatement(sql);

            statement.setLong(1, id);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                long parentCommentId = resultSet.getLong(5);
                if (resultSet.wasNull()) {
                    parentCommentId = -1;
                }
                result.add(new Comment(resultSet.getLong(1),
                        resultSet.getString(2),
                        resultSet.getLong(3),
                        resultSet.getInt(4),
                        parentCommentId,
                        resultSet.getString(6),
                        resultSet.getTimestamp(7)));
            }

            return result;
        } catch (ClassNotFoundException | SQLException e) {
            return null;
        } finally {
            close();
        }
    }

    public void addComment(Comment comment) throws CommentCreationException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            connection = DriverManager.getConnection(host + dbName + "?useSSL=false&allowPublicKeyRetrieval=true",
                    dbUserName, dbUserPassword);

            long authorId = 0;

            String selectAuthorSql = "SELECT user_id FROM users WHERE name = ? ";

            statement = connection.prepareStatement(selectAuthorSql);
            statement.setString(1, comment.getAuthor());

            ResultSet resultSet = statement.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                throw new CommentCreationException("Please log in to create new comment");
            }

            while (resultSet.next()) {
                authorId = resultSet.getLong(1);
            }

            String sql = "INSERT INTO comments(author, parent, points, parent_comment, comment_text, creation_date) " +
                    "VALUES( ? ,  ? ,  ? , ? , ? , now())";


            long parentCommentId = comment.getParentCommentId();

            statement = connection.prepareStatement(sql);

            statement.setLong(1, authorId);
            statement.setLong(2, comment.getParentId());
            statement.setInt(3, comment.getPoints());
            if (parentCommentId != -1) {
                statement.setLong(4, comment.getParentCommentId());
            } else {
                statement.setNull(4, Types.INTEGER);
            }
            statement.setString(5, comment.getText());

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
