package sk.tuke.gamestudio.service;

import sk.tuke.gamestudio.entity.Comment;
import sk.tuke.gamestudio.entity.Score;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CommentServiceJDBC implements CommentService {
    private static final String CREATE = "CREATE TABLE IF NOT EXISTS comment (game VARCHAR(64) NOT NULL, player VARCHAR(64) NOT NULL, comment VARCHAR NOT NULL, commentedOn TIMESTAMP NOT NULL);";
    public static final String DELETE = "DELETE FROM comment";
    public static final String INSERT = "INSERT INTO comment (game, player, comment, commentedOn) VALUES (?, ?, ?, ?)";
    public static final String SELECT = "SELECT game, player, comment, commentedOn FROM comment WHERE game = ? ORDER BY commentedOn DESC";

    public static final String URL = "jdbc:postgresql://localhost:5432/gamestudio";
    public static final String USER = "postgres";
    public static final String PASSWORD = "postgres";

    @Override
    public void addComment(Comment comment) throws CommentException {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement statement = connection.createStatement();
             PreparedStatement insertStatement = connection.prepareStatement(INSERT)
        ) {
            statement.executeUpdate(CREATE);
            insertStatement.setString(1, comment.getGame());
            insertStatement.setString(2, comment.getPlayer());
            insertStatement.setString(3, comment.getComment());
            insertStatement.setTimestamp(4, new Timestamp(comment.getCommentedOn().getTime()));
            insertStatement.executeUpdate();
        } catch (SQLException e) {
            throw new CommentException("Problem inserting comment", e);
        }
    }

    @Override
    public List<Comment> getComments(String game) throws CommentException {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement  statement = connection.createStatement();
             PreparedStatement selectStatement = connection.prepareStatement(SELECT);
        ) {
            statement.executeUpdate(CREATE);
            selectStatement.setString(1, game);
            try (ResultSet rs = selectStatement.executeQuery()) {
                List<Comment> comments = new ArrayList<>();
                while (rs.next()) {
                    comments.add(new Comment(rs.getString(1), rs.getString(2), rs.getString(3), rs.getTimestamp(4)));
                }
                return comments;
            }
        } catch (SQLException e) {
            throw new ScoreException("Problem selecting score", e);
        }
    }

    @Override
    public void reset() throws CommentException {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement statement = connection.createStatement();
        ) {
            statement.executeUpdate(CREATE);
            statement.executeUpdate(DELETE);
        } catch (SQLException e) {
            throw new CommentException("Problem deleting comment", e);
        }
    }
}
