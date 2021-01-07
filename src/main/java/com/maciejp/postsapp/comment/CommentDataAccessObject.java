package com.maciejp.postsapp.comment;

import com.maciejp.postsapp.exception.CommentCreationException;
import com.maciejp.postsapp.exception.UpdatePointsException;
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

            String sql = "SELECT comment_id, u.name, parent, p.score, parent_comment, comment_text, creation_date " +
                    "FROM (comments JOIN users AS u ON comments.author = u.user_id) " +
                    "LEFT JOIN (SELECT comment , SUM(value) AS score FROM points GROUP BY comment) AS p " +
                    "ON p.comment = comment_id WHERE parent = ? " +
                    "ORDER BY score DESC, creation_date DESC";

            connection = DriverManager.getConnection(host + dbName + "?useSSL=false&allowPublicKeyRetrieval=true",
                    dbUserName, dbUserPassword);
            statement = connection.prepareStatement(sql);

            statement.setLong(1, id);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Long parentCommentId = resultSet.getLong(5);
                if (resultSet.wasNull()) {
                    parentCommentId = null;
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
            e.printStackTrace();
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

            String sql = "INSERT INTO comments(author, parent, parent_comment, comment_text, creation_date) " +
                    "VALUES( ? , ? , ? , ? , now())";


            long parentCommentId = comment.getParentCommentId();

            statement = connection.prepareStatement(sql);

            statement.setLong(1, authorId);
            statement.setLong(2, comment.getParentId());
            if (parentCommentId != -1) {
                statement.setLong(3, comment.getParentCommentId());
            } else {
                statement.setNull(3, Types.INTEGER);
            }
            statement.setString(4, comment.getText());

            statement.executeUpdate();

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            close();
        }
    }

    public void updatePoints(String author, long id, int value) throws UpdatePointsException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            connection = DriverManager.getConnection(host + dbName + "?useSSL=false&allowPublicKeyRetrieval=true",
                    dbUserName, dbUserPassword);

            long authorId = 0;

            String selectAuthorSql = "SELECT user_id FROM users WHERE name = ? ";

            statement = connection.prepareStatement(selectAuthorSql);
            statement.setString(1, author);

            ResultSet resultSet = statement.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                throw new UpdatePointsException("Please log in to create new comment");
            }

            while (resultSet.next()) {
                authorId = resultSet.getLong(1);
            }

            // TODO select first

            String sql = "SELECT * FROM points WHERE author = ? AND comment = ? ";

            statement = connection.prepareStatement(sql);

            statement.setLong(1, authorId);
            statement.setLong(2, id);

            resultSet = statement.executeQuery();

            if (resultSet.isBeforeFirst()) {
                throw new UpdatePointsException("Points already updated by this author");
            }

            sql = "INSERT INTO points(author, comment, value, date) VALUES ( ? , ? , ? , now())";

            statement = connection.prepareStatement(sql);

            statement.setLong(1, authorId);
            statement.setLong(2, id);
            statement.setLong(3, value);

            statement.executeUpdate();

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            close();
        }
    }

    public int selectCommentScore(long id) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            int result = 0;

            String sql = "SELECT p.score " +
                    "FROM (comments JOIN users AS u ON comments.author = u.user_id) " +
                    "LEFT JOIN (SELECT comment , SUM(value) AS score " +
                    "FROM points GROUP BY comment) " +
                    "AS p ON p.comment = comment_id WHERE comment_id = ? ";

            connection = DriverManager.getConnection(host + dbName + "?useSSL=false&allowPublicKeyRetrieval=true",
                    dbUserName, dbUserPassword);
            statement = connection.prepareStatement(sql);

            statement.setLong(1, id);

            ResultSet resultSet = statement.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                return 0;
            }

            while (resultSet.next()) {
                result = resultSet.getInt(1);
            }

            return result;
        } catch (ClassNotFoundException | SQLException e) {
            return 0;
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
