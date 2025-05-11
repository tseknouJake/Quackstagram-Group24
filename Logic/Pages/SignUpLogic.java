package Logic.Pages;

import Logic.LogicClass;
import ParameterClasses.Following;
import ParameterClasses.User;
import TableManaging.DAOs.UserDAO;
import TableManaging.DB;
import TableManaging.DBConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Class that handles the logic of SignUpUI
 */
public class SignUpLogic extends LogicClass{

    /**
     * Signs up the new user
     * @param newUser
     * @throws Exception if username already exists
     */
    public static void signUpUser(User newUser) throws Exception {
        String username = newUser.getUsername();

        try (Connection conn = DBConnectionManager.getConnection()) {
            UserDAO userDao = new UserDAO(conn);

            // 1) ensure uniqueness
            if (userDao.findByUsername(username).isPresent()) {
                throw new Exception("Username already exists, choose a different one.");
            }

            // 2) insert into users table
            userDao.create(newUser);

            // 3) initialize login state (logged out by default)
            String sql = "INSERT INTO user_login_state(username, is_logged_in) VALUES (?, ?)";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, username);
                ps.setBoolean(2, false);
                ps.executeUpdate();
            }
        } catch (SQLException sqle) {
            throw new Exception("Failed to sign up user: " + sqle.getMessage(), sqle);
        }

        // 4) store in-memory so UI can pick it up
        saveCurrUserInformation(newUser);
    }



//    /**
//     * Adds the new user to all relevant tables
//     */
//    private static void addUser(User user) {
//
//        // adds user to USERS table
//        DB.USERS.insertRow(user, true);
//
//        // creates a new empty following object and adds it to the FOLLOWING table
//        Following userFollowing = new Following(user.getUsername());
//        DB.FOLLOWING.insertRow(userFollowing, true);
//    }

}
