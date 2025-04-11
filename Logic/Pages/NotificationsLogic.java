package Logic.Pages;

import Logic.LogicClass;
import ParameterClasses.Notification;
import TableManaging.DB;

import java.util.ArrayList;
import java.util.Collections;
import java.util.function.Predicate;


/**
 * Class that handles the logic of NotificationsUI
 */
public class NotificationsLogic extends LogicClass {

    /**
     * @return an ArrayList of all relevant notifications for the current user
     */
    public static ArrayList<Notification> fetchNotifications(){
        String currUser = getCurrUser().getUsername();
        Predicate<Notification> condition = notif -> notif.getRecievingUser().equals(currUser);
        ArrayList<Notification> queryResult = DB.NOTIFICATIONS.fetchRows(condition); 
        return reverseArrayOrder(queryResult);
    }

    /**
     * Makes sure most current notifications are on top older ones
     * @param arr
     * @return
     */
    private static ArrayList<Notification> reverseArrayOrder(ArrayList<Notification> arr){
        Collections.reverse(arr);
        return arr;
    }

}
