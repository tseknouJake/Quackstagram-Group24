package Logic;

import java.util.ArrayList;
import java.util.function.Predicate;

import ParameterClasses.User;
import TableManaging.DB;

/**
 * Class to group together logic-handling classes and implement most used methods in those classes
 */
public abstract class LogicClass {
        
    /**
     * Recieves a username and returns the User object of that username
     * @return A User object if the User exists, null otherwise
     */
    public static User getUser(String username){
        Predicate<User> condition = user -> user.getUsername().equals(username);
        ArrayList<User> queryResult = DB.USERS.fetchRows(condition); 
        if(queryResult.size() != 0){
            return queryResult.get(0);
        }
        return null;
    }
    
    /**
     * Fetches the current user from DB.CURR_USER 
     * @return the User object of the current user
     */
    public static User getCurrUser(){
        User currUser = DB.CURR_USER.fetchRows(null).get(0);
        return currUser;
    }

    /**
     * Recieves a user and returns whether it is not null
     * @param user
     * @return false if the user is null, true otherwise
     */
    public static boolean userExists(User user) {
        return user != null;
    }


    /**
     * Saves the given user as the current user
     */
    public static void saveCurrUserInformation(User user) {
        DB.CURR_USER.insertRow(user, false);
    }
}
