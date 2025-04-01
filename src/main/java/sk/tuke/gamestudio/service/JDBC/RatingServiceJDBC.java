package sk.tuke.gamestudio.service.JDBC;

import sk.tuke.gamestudio.entity.Rating;
import sk.tuke.gamestudio.service.RatingException;
import sk.tuke.gamestudio.service.RatingService;

import javax.sql.DataSource;
import java.sql.*;

import static java.lang.Math.round;

public class RatingServiceJDBC implements RatingService {
//    private static final String CREATE = "CREATE TABLE IF NOT EXISTS rating (game VARCHAR(64) NOT NULL, player VARCHAR(64) NOT NULL, rating INT NOT NULL, ratedOn TIMESTAMP NOT NULL, PRIMARY KEY (game, player));";
    public static final String DELETE = "DELETE FROM rating";
    public static final String SELECT = "SELECT * FROM rating WHERE game = ? AND player = ? LIMIT 1;";
    public static final String AVERAGE = "SELECT AVG(rating) FROM rating WHERE game = ?;\n";
    public static final String UPDATE =
            "INSERT INTO rating (game, player, rating, ratedOn) VALUES (?, ?, ?, ?) " +
                    "ON CONFLICT (game, player) DO UPDATE SET rating = EXCLUDED.rating, ratedOn = EXCLUDED.ratedOn;";

    private DataSource dataSource;

    public RatingServiceJDBC(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void setRating(Rating rating) throws RatingException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement updateStatement = connection.prepareStatement(UPDATE);
        ) {
            updateStatement.setString(1, rating.getGame());
            updateStatement.setString(2, rating.getPlayer());
            updateStatement.setInt(3, rating.getRating());
            updateStatement.setTimestamp(4, new Timestamp(rating.getRatedOn().getTime()));
            updateStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RatingException("Problem selecting score", e);
        }
    }

    @Override
    public int getAverageRating(String game) throws RatingException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement getAverageStatement = connection.prepareStatement(AVERAGE);
        ) {
            getAverageStatement.setString(1, game);
            try (ResultSet rs = getAverageStatement.executeQuery()) {
                if (rs.next()) {
                    if (rs.wasNull())
                        return 0;
                    return (int) round(rs.getDouble(1));
                } else {
                    return 0;
                }
            }
        } catch (SQLException e) {
            throw new RatingException("Problem selecting score", e);
        }
    }

    @Override
    public int getRating(String game, String player) throws RatingException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement selectStatement = connection.prepareStatement(SELECT);
        ) {
            selectStatement.setString(1, game);
            selectStatement.setString(2, player);
            try (ResultSet rs = selectStatement.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("rating");
                } else {
                    return 0;
                }
            }
        } catch (SQLException e) {
            throw new RatingException("Problem selecting score", e);
        }
    }

    @Override
    public void reset() throws RatingException {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
        ) {
            statement.executeUpdate(DELETE);
        } catch (SQLException e) {
            throw new RatingException("Problem deleting rating", e);
        }
    }
}
