package TableManaging;

import java.sql.SQLException;

import ParameterClasses.User;
import ParameterClasses.LoginState;
import TableManaging.DAOs.FollowingDAO;
import TableManaging.DAOs.UserDAO;
//import TableManaging.DAOs.LoginStateDAO;
//import TableManaging.DAOs.FollowingDAO;
//import TableManaging.DAOs.PostDAO;
//import TableManaging.DAOs.LikeDAO;
//import TableManaging.DAOs.NotificationDAO;
import ParameterClasses.Following;
import ParameterClasses.Post;
//import ParameterClasses.Like;
import ParameterClasses.Notification;

public class DB {

    public static final Table<User>               USERS;
    public static final Table<Following>          FOLLOWING;
//    public static final Table<Post>               POSTS;
//    public static final Table<Like>               LIKES;
//    public static final Table<Notification>       NOTIFICATIONS;
//    public static final Table<LoginState>         USER_LOGIN_STATE;

    static {
        try {
            var conn = DBConnectionManager.getConnection();

            USERS            = new TableDB<>("users",             new UserDAO(conn),         new UserDAO(conn));
            FOLLOWING        = new TableDB<>("followers",         new FollowingDAO(conn),    new FollowingDAO(conn));
//            POSTS            = new TableDB<>("posts",             new PostDAO(conn),         new PostDAO(conn));
//            LIKES            = new TableDB<>("likes",             new LikeDAO(conn),         new LikeDAO(conn));
//            NOTIFICATIONS    = new TableDB<>("notifications",     new NotificationDAO(conn), new NotificationDAO(conn));
//            USER_LOGIN_STATE = new TableDB<>("user_login_state",  new LoginStateDAO(conn),   new LoginStateDAO(conn));

        } catch (SQLException e) {
            // fail fast on startup
            throw new ExceptionInInitializerError("Failed to initialize DB tables: " + e.getMessage());
        }
    }

    private DB() { /* no instances */ }
}
