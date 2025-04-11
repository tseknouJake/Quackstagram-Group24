package Logic;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * A class to deal with Timer objects and calculations
 */
public class TimeUtil {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    /**
     * Gets the current time
     */
    public static LocalDateTime getCurrentTimestamp() {
        return LocalDateTime.now();
    }

    /**
     * Recieves a String time stamp and parses it
     *
     * @param timestampStr
     * @return a LocalDateTime object of the given time stamp
     */
    public static LocalDateTime parseTimestamp(String timestampStr) {
        return LocalDateTime.parse(timestampStr, FORMATTER);
    }

    /**
     * Caculates the time the passed since the given time
     *
     * @param pastTime
     * @return A stylized string that expresses that time
     */
    public static String getTimeSince(LocalDateTime pastTime) {
        LocalDateTime now = LocalDateTime.now();
        Duration duration = Duration.between(pastTime, now);

        long seconds = duration.getSeconds();
        long minutes = seconds / 60;
        long hours = minutes / 60;
        long days = hours / 24;

        if (days >= 7) return pastTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        if (days > 0) return days + " day" + (days > 1 ? "s" : "") + " ago";
        if (hours > 0) return hours + " hour" + (hours > 1 ? "s" : "") + " ago";
        if (minutes > 0) return minutes + " minute" + (minutes > 1 ? "s" : "") + " ago";
        return "Just now";
    }

}

