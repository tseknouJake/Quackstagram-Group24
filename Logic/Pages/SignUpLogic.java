package Logic.Pages;

import Logic.LogicClass;
import ParameterClasses.Following;
import ParameterClasses.User;
import TableManaging.DB;

/**
 * Class that handles the logic of SignUpUI
 */
public class SignUpLogic extends LogicClass{

    /**
     * Signs up the new user
     * @param newUser
     * @throws Exception if username already exists
     */
    public static void signUpUser(User newUser) throws Exception{
        String username = newUser.getUsername();
        if (userExists(getUser(username))){
            throw new Exception("Username already exists, choose a different one.");
        }
        addUser(newUser);
        saveCurrUserInformation(newUser);
    }



    /**
     * Adds the new user to all relevant tables
     */
    private static void addUser(User user) {
        
        // adds user to USERS table
        DB.USERS.insertRow(user, true);

        // creates a new empty following object and adds it to the FOLLOWING table
        Following userFollowing = new Following(user.getUsername());
        DB.FOLLOWING.insertRow(userFollowing, true);
    }

}
