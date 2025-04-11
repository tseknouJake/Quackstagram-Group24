package Logic.Pages;

import Logic.LogicClass;
import ParameterClasses.Following;
import ParameterClasses.Notification;
import ParameterClasses.Post;
import TableManaging.DB;

import java.util.ArrayList;
import java.util.function.Predicate;

/**
 * Class that handles the logic of QuackstagramHomeUI
 */
public class QuakstagramHomeLogic extends LogicClass {

    /**
     * Like a given post (by the current user)
     * updates all relevant tables
     */
    public static void likePost(Post likedPost) {
        likedPost = likedPost.like(getCurrUser());

        String postUsername = likedPost.getUsername();
        int postID = likedPost.getID();

        Predicate<Post> condition = post -> {
            boolean usernameMatches = post.getUsername().equals(postUsername);
            boolean idMatches = post.getID() == postID;
            return usernameMatches && idMatches;
        };

        // updates the post's likes
        DB.POSTS.updateRows(condition, likedPost);

        // adds a new notification
        DB.NOTIFICATIONS.insertRow(new Notification(postUsername, "like"), true);
    }


    /**
     * Recieves a Following object of all the users the current user is following
     */
    private static ArrayList<String> getFollowingOfCurrUser() {
        String currUsername = getCurrUser().getUsername();
        Predicate<Following> condition = following -> following.getUsername().equals(currUsername);
        Following followingList = DB.FOLLOWING.fetchRows(condition).get(0);
        System.out.println("following : " + followingList.getFollowing().size());
        return followingList.getFollowing();
    }

    /**
     * Fetches all the posts of all the users that the current user is following
     */
    public static ArrayList<Post> getAllFollowingPosts() {
        ArrayList<String> followingUsers = getFollowingOfCurrUser();
        ArrayList<Post> queryResult = DB.POSTS.fetchRows(null);
        ArrayList<Post> posts = new ArrayList<>();

        for (Post post : queryResult) {
            for (String username : followingUsers) {
                if (post.getUsername().equals(username)) {
                    posts.add(post);
                    break;
                }
            }
        }

        System.out.println("post #:" + posts.size());
        return posts;

    }

    /**
     * Checks if the current user already liked the post
     * @return true if already liked, false otherwise
     */
    public static boolean didUserAlreadyLike(Post post) {
        return post.didUserLike(getCurrUser());
    }


    /**
     * Unlikes a post and updates POSTS table
     */
    public static void unLikePost(Post unlikedPost) {

        unlikedPost = unlikedPost.unLike(getCurrUser());

        String postUsername = unlikedPost.getUsername();
        int postID = unlikedPost.getID();

        Predicate<Post> condition = post -> {
            boolean usernameMatches = post.getUsername().equals(postUsername);
            boolean idMatches = post.getID() == postID;
            return usernameMatches && idMatches;
        };

        // updates the post's likes
        DB.POSTS.updateRows(condition, unlikedPost);

    }

}
