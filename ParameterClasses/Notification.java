package ParameterClasses;

import java.time.LocalDateTime;

import Logic.LogicClass;
import Logic.TimeUtil;

/**
 * An object that represents a notification 
 */
public class Notification {

    private String recievingUser;
    private String actionUser;
    private String action;
    private LocalDateTime notificationTime;


    /**
     * Constructor for notification object
     * @param recievingUser
     * @param actionUser
     * @param action
     * @param notificationTime
     */
    public Notification(String recievingUser, String actionUser, String action, LocalDateTime notificationTime) {
        this.recievingUser = recievingUser;
        this.actionUser = actionUser;
        this.action = action;
        this.notificationTime = notificationTime;
    }

    /**
     *  Constructor for new notification object (inputs time posted and initializes the liked users list)
     * @param recievingUser
     * @param action
     */
    public Notification(String recievingUser, String action){
        User actionUser = LogicClass.getCurrUser();
        this(recievingUser, actionUser.getUsername(), action, TimeUtil.getCurrentTimestamp());
    }

    /**
     * Returns all relevant information in a csv format
     */
    @Override
    public String toString() {
        return recievingUser + "," + actionUser + "," + action + "," + notificationTime;
    }

    /**
     * get methods for all notification attributes
     */
    public String getactionUser() {
        return this.actionUser;
    }

    public String getRecievingUser() {
        return this.recievingUser;
    }

    public LocalDateTime getNotificationTime() {
        return this.notificationTime;
    }

    public String getAction() {
        return this.action;
    }


    /**
     * Returns notification information in a readable String format
     */
    public String printNotification() {
        String formattedDate = TimeUtil.getTimeSince(this.notificationTime);

        if (action.equals("like")){
            return actionUser + " liked your post - " + formattedDate;
        }
        else if(action.equals("follow")){
            return actionUser + " followed you - " + formattedDate;
        }
        else{
            return "Unknown notification";
        }   
    }


}


