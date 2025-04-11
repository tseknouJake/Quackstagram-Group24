package TableManaging;

import ParameterClasses.Following;
import ParameterClasses.Notification;
import ParameterClasses.Post;
import ParameterClasses.User;
import TableManaging.Parsers.FollowParser;
import TableManaging.Parsers.NotificationParser;
import TableManaging.Parsers.PostParser;
import TableManaging.Parsers.UserParser;
import java.io.File;
 

/**
 * Class that holds all tables
 */
public class DB{

    public static final String TABLES_FOLDER = "Tables";

    /**
     * Table of User object that takes data from users.txt
     */
    public static final TableTXT<User> USERS = new TableTXT<>(new File(TABLES_FOLDER, "users.txt"), new UserParser());

    /**
     * Table of Post object that takes data from posts.txt
     */
    public static final TableTXT<Post> POSTS = new TableTXT<>(new File(TABLES_FOLDER, "posts.txt"), new PostParser());

    /**
     * Table of Notification object that takes data from notifications.txt
     */
    public static final TableTXT<Notification> NOTIFICATIONS = new TableTXT<>(new File(TABLES_FOLDER, "notifications.txt"), new NotificationParser());

    /**
     * Table of Following object that takes data from following.txt
     */
    public static final TableTXT<Following> FOLLOWING = new TableTXT<>(new File(TABLES_FOLDER, "following.txt"), new FollowParser());

    /**
     * Table of User object that takes data from current_user.txt
     */
    public static final TableTXT<User> CURR_USER = new TableTXT<>(new File(TABLES_FOLDER, "current_user.txt"), new UserParser());



}
