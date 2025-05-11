package ParameterClasses;

public class Likes {
    private final String username;
    private final int postId;

    public Likes(String username, int postId) {
        this.username = username;
        this.postId  = postId;
    }

    public String getUsername() { return username; }
    public int    getPostId()   { return postId; }
}