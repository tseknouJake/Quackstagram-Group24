package Logic.Pages;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.function.Predicate;

import Logic.LogicClass;
import Logic.TimeUtil;
import ParameterClasses.User;

import static TableManaging.DB.USERS;

/**
 * Class that handles the logic of ShowPostUI
 */
public class ShowPostLogic extends LogicClass{

    /**
     * Calculates the time that passed since posting
     * @return a stylized time string
     */
    public static String calculateTimeSincePosting(LocalDateTime postTime) {
        return TimeUtil.getTimeSince(postTime);
    }

    /**
     * Checks if the owner of the viewed post is the current user
     * @param profileUser - owner of the current post
     * @return true if the post belongs to the current user, false otherwise
     */
    public static boolean isLoggedInUser(String profileUser){
        Predicate<User> condition = user -> user.getUsername().equals(profileUser);
        ArrayList<User> queryResult = USERS.fetchRows(condition);
        User currUser = getCurrUser();
        User otherUser = queryResult.get(0);
        return currUser.equals(otherUser);
    }

}
