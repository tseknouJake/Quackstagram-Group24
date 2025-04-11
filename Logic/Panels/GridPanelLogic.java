package Logic.Panels;

import java.util.ArrayList;
import java.util.function.Predicate;

import Logic.LogicClass;
import ParameterClasses.Post;
import ParameterClasses.User;
import TableManaging.DB;

/**
 * Class that handles the logic of GridPanelUI
 */
public class GridPanelLogic extends LogicClass{

    /**
     * Recieve all the posts from the given user
     * @param user
     * @return an ArrayList of the object posts. If user has no posts - returns empty ArrayList
     */
    public static ArrayList<Post> getUserPosts(User user){
        Predicate<Post> condition = post -> post.getUsername().equals(user.getUsername());
        ArrayList<Post> queryResult = DB.POSTS.fetchRows(condition); 

        return queryResult;
    }


    /**
     * Returns a list of all posts not from current user
     */
    public static ArrayList<Post> getAllOtherUserPosts(){
        String currUsername = getCurrUser().getUsername();
        ArrayList<Post> queryResult = DB.POSTS.fetchRows(null); 
        ArrayList<Post> posts = new ArrayList<>();

        for (Post post : queryResult){
            if (!post.getUsername().equals(currUsername)){
                posts.add(post);
            }
        }

        return posts;
    }

    /**
     * Fetches all relevant posts for the grid
     * If this is a post grid for a user profile - fetches all user posts
     * If this is the explore page post grid - fetches all posts that are not the current users
     * @param user - the given user (in case of profile), null if explore page
     * @return ArrayList of the type Post
     */
    public static ArrayList<Post> fetchAllPosts(User user){
        if (userExists(user)){
            return getUserPosts(user);
        }
        return getAllOtherUserPosts();
    }

}
