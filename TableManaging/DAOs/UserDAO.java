package TableManaging.DAOs;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import ParameterClasses.User;

/**
 * DAO for the `users` table.
 * Implements data mapping (ResultSet -> User) and parameter binding (User -> PreparedStatement).
 */
public class UserDAO implements RowMapper<User>, StatementBinder<User> {

    private final Connection conn;

    public UserDAO(Connection conn) {
        this.conn = conn;
    }

    @Override
    public User mapRow(ResultSet rs) throws SQLException {
        String username = rs.getString("username");
        String password = rs.getString("password");
        String bio = rs.getString("bio");
        String picPath = rs.getString("profile_pic_path");
        return new User(username, password, bio, picPath);
    }

    // --- StatementBinder<User> ---
    @Override
    public String[] getColumns() {
        return new String[]{"username", "password", "bio", "profile_pic_path"};
    }

    @Override
    public void bind(PreparedStatement ps, User u) throws SQLException {
        ps.setString(1, u.getUsername());
        ps.setString(2, u.getPassword());
        ps.setString(3, u.getBio());
        ps.setString(4, u.getProfilePicPath());
    }

    /**
     * Fetch all users.
     */
    public List<User> findAll() throws SQLException {
        String sql = "SELECT * FROM users";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            List<User> list = new ArrayList<>();
            while (rs.next()) {
                list.add(mapRow(rs));
            }
            return list;
        }
    }

    /**
     * Find a user by username.
     */
    public Optional<User> findByUsername(String username) throws SQLException {
        String sql = "SELECT * FROM users WHERE username = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRow(rs));
                }
                return Optional.empty();
            }
        }
    }

    /**
     * Insert a new user.
     */
    public void create(User u) throws SQLException {
        String[] cols = getColumns();
        String colList = String.join(",", cols);
        String placeholders = String.join(",", java.util.Collections.nCopies(cols.length, "?"));
        String sql = "INSERT INTO users(" + colList + ") VALUES (" + placeholders + ")";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            bind(ps, u);
            ps.executeUpdate();
        }
    }

    /**
     * Update only the bio field for a given user.
     */
    public void updateBio(String username, String newBio) throws SQLException {
        String sql = "UPDATE users SET bio = ? WHERE username = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, newBio);
            ps.setString(2, username);
            ps.executeUpdate();
        }
    }

    /**
     * Delete a user by username.
     */
    public void delete(String username) throws SQLException {
        String sql = "DELETE FROM users WHERE username = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.executeUpdate();
        }
    }
}
