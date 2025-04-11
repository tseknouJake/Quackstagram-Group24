package Logic.Pages;

import Logic.LogicClass;
import ParameterClasses.User;

/**
 * Class that handles the logic of ExploreUI
 */
public class ExploreUILogic extends LogicClass {

    /**
     * Searches for a user given a String username
     * @return the user if found or throws an exception saying a user was not found.
     */
    public static User performSearch(String searchedUsername) throws Exception{
        User user = getUser(searchedUsername);
        if (user != null) {
            return user;
        }
        throw new Exception("No user found.");
    }
}
