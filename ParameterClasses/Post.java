package ParameterClasses;


import Logic.TimeUtil;
import Logic.Pages.ShowPostLogic;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * An object that represents a post
 */
public class Post {

    protected String username;
    protected int id;
    protected String caption;
    protected LocalDateTime timePosted;
    protected String path;
    protected ArrayList<String> likedUsers;

    /**
     * Constructor for post object
     * @param username
     * @param id
     * @param path
     * @param caption
     * @param timePosted
     * @param likedUsers
     */
    public Post(String username, int id, String path, String caption, LocalDateTime timePosted, ArrayList<String> likedUsers) {
        this.username = username;
        this.id = id;
        this.path = path;
        this.caption = caption;
        this.timePosted = timePosted;
        this.likedUsers = likedUsers;
    }

    /**
     * Constructor for new post object (inputs time posted and initializes the liked users list)
     * @param username
     * @param path
     * @param caption
     */
    public Post(String username, String path, String caption){
        int id = ShowPostLogic.getUser(username).getPostsCount() + 1;
        this(username, id, path, caption, TimeUtil.getCurrentTimestamp(), new ArrayList<>());
    }

    /**
     * Like a post by a given user
     * @param user
     * @return the updated Post object
     */
    public Post like(User user) {
        likedUsers.add(user.getUsername());
        return this;
    }

    /**
     * Unlike a post by a given user
     * @param user
     * @return the updated Post object
     */
    public Post unLike(User user){
        likedUsers.remove(user.getUsername());
        return this;
    }

    /**
     * getters for post attributes
     */
    public String getCaption() { return caption; }
    public ArrayList<String> getLikedUsers() { return likedUsers; }
    public String getUsername() { return username; }
    public String getPath(){return path;}
    public LocalDateTime getTimePosted() { return timePosted; }
    public int getLikesCount() {return likedUsers.size(); }
    public int getID() {return id; }


    /**
     * Returns all relevant information in a csv format
     */
    @Override
    public String toString() {
        if (likedUsers.size() > 0){
            String likedUsersString = String.join(",", likedUsers);
            return  username + "," + id + "," + caption + "," + path + "," + timePosted + "," + likedUsersString;
        }
        return username + "," + id + "," + caption + "," + path + "," + timePosted;
    }

    /**
     * Checks if a given user already likes the post
     * @param user
     * @return true if user already likes, false otherwise
     */
    public boolean didUserLike(User user){
        for (String username : likedUsers){
            if (username.equals(user.getUsername())){
                return true;
            }
        }
        return false;
    }

}
