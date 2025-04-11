package TableManaging.Parsers;


import Logic.TimeUtil;

import java.time.LocalDateTime;

import ParameterClasses.Notification;

/**
 * A parser for the Notifications
 * see Parser interface for method comments
 */
public class NotificationParser implements Parser<Notification> {
    
    @Override
    public Notification parseRow(String csvLine) {

        String[] csvList = csvLine.split(",");

        String recievingUser = csvList[0];
        String actionUser = csvList[1];
        String action = csvList[2];
        LocalDateTime notificationTime = TimeUtil.parseTimestamp(csvList[3]);

        return new Notification(recievingUser, actionUser, action, notificationTime);
    }

    @Override
    public String toCSV(Notification row) {
        return row.toString();
    }


}
