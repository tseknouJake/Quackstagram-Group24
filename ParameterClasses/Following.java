package ParameterClasses;
import java.util.ArrayList;

/**
 * An object that represents a user and all other users it is following
 */
public class Following {

    private String username;
    private ArrayList<String> followingList;

    /**
     * Constructor for following object
     * @param username - the current user username
     * @param followingList - a list of String usernames that the current user is following
     */
    public Following(String username, ArrayList<String> followingList) {
        this.username = username;
        this.followingList = followingList;
    }

    /**
     * Constructor for following object (for a new user that is currently not following anyone)
     * @param username - the current user username
     */
    public Following(String username) {
        this(username, new ArrayList<>());
    }

    public String getUsername() {
        return username;
    }

    public ArrayList<String> getFollowing() {
        return new ArrayList<>(followingList);
    }

    /**
     * Follows a given user
     * @param profileUsename - user to follow
     * @return - the updated Following object
     */
    public Following followUser(String profileUsename) {
        followingList.add(profileUsename);
        return this;
    }


    /**
     * Unfollows a given user
     * @param profileUsename - user to unfollow
     * @return - the updated Following object
     */
    public Following unfollowUser(String profileUsename) {
        followingList.remove(profileUsename);
        return this;
    }

    /**
     * Returns all relevant information in a csv format
     */
    @Override
    public String toString() {
        if (followingList.size() > 0) {
            String followingCsv = String.join(",", followingList);
            return username + "," + followingCsv;
        }
        return username;
    }


}
