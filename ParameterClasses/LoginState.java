package ParameterClasses;

public class LoginState {
    private final String username;
    private final boolean isLoggedIn;

    public LoginState(String username, boolean isLoggedIn) {
        this.username   = username;
        this.isLoggedIn = isLoggedIn;
    }

    public String getUsername() {
        return username;
    }

    public boolean isLoggedIn() {
        return isLoggedIn;
    }
}