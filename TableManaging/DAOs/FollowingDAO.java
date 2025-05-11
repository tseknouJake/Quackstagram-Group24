package TableManaging.DAOs;

import ParameterClasses.Following;
import ParameterClasses.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FollowingDAO implements RowMapper<User>, StatementBinder<User>{
    private final Connection conn;

    public FollowingDAO(Connection conn) {
        this.conn = conn;
    }

    @Override
    public Following mapRow(ResultSet rs) throws SQLException {
        String follower = rs.getString("follower_username");
        String followed = rs.getString("followed_username");
        return new Following(follower, followed);
    }

    @Override
    public void bind(PreparedStatement ps, Following obj) throws SQLException {
        ps.setString(1, obj.getFollowerUsername());
        ps.setString(2, obj.getFollowedUsername());
    }

    /**
     * Find a specific follow relationship by both usernames.
     */
    public Optional<Following> find(String followerUsername, String followedUsername) throws SQLException {
        String sql = "SELECT * FROM followers WHERE follower_username = ? AND followed_username = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, followerUsername);
            ps.setString(2, followedUsername);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRow(rs));
                }
                return Optional.empty();
            }
        }
    }

    /**
     * List all users who follow the given user.
     */
    public List<Following> findFollowers(String followedUsername) throws SQLException {
        String sql = "SELECT * FROM followers WHERE followed_username = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, followedUsername);
            try (ResultSet rs = ps.executeQuery()) {
                List<Following> list = new ArrayList<>();
                while (rs.next()) {
                    list.add(mapRow(rs));
                }
                return list;
            }
        }
    }

    /**
     * List all users that the given user is following.
     */
    public List<Following> findFollowing(String followerUsername) throws SQLException {
        String sql = "SELECT * FROM followers WHERE follower_username = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, followerUsername);
            try (ResultSet rs = ps.executeQuery()) {
                List<Following> list = new ArrayList<>();
                while (rs.next()) {
                    list.add(mapRow(rs));
                }
                return list;
            }
        }
    }

    /**
     * Create a new follow relationship.
     */
    public void create(Following obj) throws SQLException {
        String sql = "INSERT INTO followers (follower_username, followed_username) VALUES (?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            bind(ps, obj);
            ps.executeUpdate();
        }
    }

    /**
     * Delete a follow relationship.
     */
    public void delete(String followerUsername, String followedUsername) throws SQLException {
        String sql = "DELETE FROM followers WHERE follower_username = ? AND followed_username = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, followerUsername);
            ps.setString(2, followedUsername);
            ps.executeUpdate();
        }
    }

    @Override
    public void bind(PreparedStatement ps, User obj) throws SQLException {

    }
}
