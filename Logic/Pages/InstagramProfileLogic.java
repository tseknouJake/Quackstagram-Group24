package Logic.Pages;

import java.util.ArrayList;
import java.util.function.Predicate;

import Logic.LogicClass;
import ParameterClasses.Following;
import ParameterClasses.Notification;
import ParameterClasses.Post;
import ParameterClasses.User;
import TableManaging.DB;


/**
 * Class that handles the logic of InstagramProfileUI
 */
public class InstagramProfileLogic extends LogicClass{

    /**
     * Recieves a user and checks if it is the current user
     * @return true if same user, false otherwise
     */
    public static boolean isLoggedInUser(User profileUser){
        User currUser = getCurrUser();
        return currUser.equals(profileUser);
    }

    /**
     * Checks if the current user if following the given user
     * @return true if current user is following, false otherwise
     */
    public static boolean isCurrUsersFollowing(User profileUser){
        User loggedInUser = getCurrUser();
        Following followingList = getFollowing(loggedInUser);
        String profileUsername = profileUser.getUsername();
        for (String following : followingList.getFollowing()){
            if (profileUsername.equals(following)){
                return true;
            }
        }
        return false;
    }


    /**
     * Adds the given user to the folllowing list of the current user
     * updates all necessary tables
     */
    public static void addFollower(User profileUser){
        User currUser = getCurrUser();
        Following currUserFollowing = getFollowing(currUser);
        String currUsername = currUser.getUsername();
        String profileUsername = profileUser.getUsername();


        //add follower
        currUserFollowing = currUserFollowing.followUser(profileUsername);    
        currUser = currUser.addfollowing();
        profileUser = profileUser.addfollower();

        //update following
        Predicate<Following> condition1 = follow -> follow.getUsername().equals(currUsername);
        DB.FOLLOWING.updateRows(condition1, currUserFollowing); 

        //update followed user
        Predicate<User> condition2 = user -> user.getUsername().equals(profileUsername);
        DB.USERS.updateRows(condition2, profileUser); 

        //update following user
        Predicate<User> condition3 = user -> user.getUsername().equals(currUsername);
        DB.USERS.updateRows(condition3, currUser); 
        saveCurrUserInformation(currUser);

        // adds a new notification
        DB.NOTIFICATIONS.insertRow(new Notification(profileUsername, "follow"), true);
    }


    /**
     * Fetches the Folllowing object of the given user
     */
    private static Following getFollowing(User profileUser){
        String username = profileUser.getUsername();
        Predicate<Following> condition = follow -> follow.getUsername().equals(username);
        return DB.FOLLOWING.fetchRows(condition).get(0);
    }
    

    /**
     * Stops following the given user and updates all relevant tables accordingly
     */
    public static void removeFollower(User profileUser){
        User currUser = getCurrUser();
        Following currUserFollowing = getFollowing(currUser);
        String currUsername = currUser.getUsername();
        String profileUsername = profileUser.getUsername();


        //remove follower
        currUserFollowing = currUserFollowing.unfollowUser(profileUsername);    
        currUser = currUser.removeFollowing();
        profileUser = profileUser.removeFollower();

        //update following
        Predicate<Following> condition1 = follow -> follow.getUsername().equals(currUsername);
        DB.FOLLOWING.updateRows(condition1, currUserFollowing); 

        //update followed user
        Predicate<User> condition2 = user -> user.getUsername().equals(profileUsername);
        DB.USERS.updateRows(condition2, profileUser); 

        //update following user
        Predicate<User> condition3 = user -> user.getUsername().equals(currUsername);
        DB.USERS.updateRows(condition3, currUser); 
        saveCurrUserInformation(currUser);

        //unlike all posts
        Predicate<Post> condition4 = post -> post.getUsername().equals(profileUsername);
        ArrayList<Post> posts = DB.POSTS.fetchRows(condition4);

        for (Post post : posts){
            if (QuakstagramHomeLogic.didUserAlreadyLike(post)){
                QuakstagramHomeLogic.unLikePost(post);
            }
        }
    }

}
