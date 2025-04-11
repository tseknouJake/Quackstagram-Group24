package ParameterClasses;


/**
 * An object that represents a user on quackstagram
 */
public class User{

    private static final String DEFAULT_PROFILE_PIC = "img/logos/DACS.png"; 

    private String username;
    private String password;
    private int postsCount;
    private int followersCount;
    private int followingCount;
    private String bio;
    private String profilePicPath;


    /**
     * Constructor for User object
     * @param username
     * @param password
     * @param postCount
     * @param followersCount
     * @param followingCount
     * @param bio
     * @param profilePicPath
     */
    public User(String username, String password, int postCount, int followersCount, int followingCount, String bio, String profilePicPath){
        this.username = username;
        this.password = password;
        this.postsCount = postCount;
        this. followersCount = followersCount;
        this.followingCount = followingCount;
        this.bio = bio;
        if (profilePicPath == null){
            this.profilePicPath = DEFAULT_PROFILE_PIC;
        }
        else{
            this.profilePicPath = profilePicPath;
        }
    }

    /**
     * Constructor for new user object (initializes postCount, followersCount & followingCount)
     * @param username
     * @param bio
     * @param password
     * @param profilePicPath
     */
    public User(String username, String bio, String password, String profilePicPath) {
        this(username, password, 0, 0, 0, bio, profilePicPath);
    }


    /**
     * getters for user attributes
     */
    public String getUsername() {
        return username;
    }
    public String getBio() { return bio; }
    public void setBio(String bio) {this.bio = bio; }
    public int getPostsCount() { return postsCount; }
    public int getFollowersCount() { return followersCount; }
    public int getFollowingCount() { return followingCount; }
    public String getProfilePicPath() { return profilePicPath; }
    private String getPassword() { return password; }


    /**
     * Adds a follower to followerCount
     * @return the updated User object
     */
    public User addfollower() {
        this.followersCount++;
        return this;
    }

    
    /**
     * Removes a follower from followerCount
     * @return the updated User object
     */
    public User removeFollower() {
        this.followersCount--;
        return this;
    }

    
    /**
     * Adds a following to followingCount
     * @return the updated User object
     */
    public User addfollowing() { 
        this.followingCount++;
        return this;
    }

    
    /**
     * Removes a following from followingCount
     * @return the updated User object
     */
    public User removeFollowing() { 
        this.followingCount--;
        return this;
    }

    /**
     * Adds a post to postCount
     * @return the updated User object
     */
    public User addPost() {
        this.postsCount++;
        return this;
    }


    /**
     * Checks whether a given password is the user's password
     * @param password
     * @return true if passwords match, false otherwise
     */
    public boolean ValidatePassword(String password){
        if(password.equals(this.getPassword())){
            return true;
        }
        return false;
    }

    /**
     * Returns all relevant information in a csv format
     */
    @Override
    public String toString() {
        return username + "," + password + "," + postsCount + "," + followersCount + "," + followingCount + "," + bio + "," + profilePicPath;
    }


    @Override
    public boolean equals(Object obj) {
        if (obj instanceof User){
            User user = (User) obj;
            return this.username.equals(user.getUsername());
        }
        return false;
    }

}