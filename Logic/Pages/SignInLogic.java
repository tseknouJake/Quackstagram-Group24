// src/Logic/Pages/SignInLogic.java
package Logic.Pages;

import Logic.LogicClass;
import ParameterClasses.User;
import TableManaging.DBConnectionManager;
import TableManaging.DAOs.UserDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SignInLogic extends LogicClass {

    /**
     * Authenticates an existing user and marks them as logged in.
     *
     * @param username the username to look up
     * @param password the raw password to verify
     * @return the loaded User object
     * @throws Exception if user not found, bad password, or DB error
     */
    public static User signInUser(String username, String password) throws Exception {
        User user;

        try (Connection conn = DBConnectionManager.getConnection()) {
            UserDAO userDao = new UserDAO(conn);

            // 1) fetch the user
            user = userDao.findByUsername(username)
                    .orElseThrow(() -> new Exception("User does not exist. Please sign up."));

            // 2) verify password
            if (!user.ValidatePassword(password)) {
                throw new Exception("Incorrect password. Please try again.");
            }

            // 3) mark as logged in
            String updateSql = "UPDATE user_login_state SET is_logged_in = ? WHERE username = ?";
            try (PreparedStatement ps = conn.prepareStatement(updateSql)) {
                ps.setBoolean(1, true);
                ps.setString(2, username);
                int updated = ps.executeUpdate();

                // if no row existed, insert it
                if (updated == 0) {
                    String insertSql = "INSERT INTO user_login_state(username, is_logged_in) VALUES (?, ?)";
                    try (PreparedStatement ps2 = conn.prepareStatement(insertSql)) {
                        ps2.setString(1, username);
                        ps2.setBoolean(2, true);
                        ps2.executeUpdate();
                    }
                }
            }
        } catch (SQLException sqle) {
            throw new Exception("Failed to sign in user: " + sqle.getMessage(), sqle);
        }

        // 4) store for UI consumption
        saveCurrUserInformation(user);
        return user;
    }
}
