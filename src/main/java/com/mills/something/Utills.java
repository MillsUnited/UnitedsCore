package com.mills.something;

import java.util.concurrent.TimeUnit;

public class Utills {

    public static String format(String string) {

        StringBuilder builder = new StringBuilder();

        for (String word : string.split("_")) {
            String first = word.substring(0, 1);
            String second = word.substring(1);

            builder.append(first.toUpperCase()).append(second.toLowerCase()).append(" ");
        }

        return builder.toString().trim();
    }

    public static String format(Enum<?> enumVal) {
        return format(enumVal.name());
    }

    public static String formatTimeAgo(long timeDifference) {
        long seconds = TimeUnit.MILLISECONDS.toSeconds(timeDifference);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(timeDifference);
        long hours = TimeUnit.MILLISECONDS.toHours(timeDifference);
        long days = TimeUnit.MILLISECONDS.toDays(timeDifference);

        if (seconds < 60) {
            return seconds + " second" + (seconds == 1 ? "" : "s") + " ago";
        } else if (minutes < 60) {
            return minutes + " minute" + (minutes == 1 ? "" : "s") + " ago";
        } else if (hours < 24) {
            return hours + " hour" + (hours == 1 ? "" : "s") + " ago";
        } else if (days < 30) {
            return days + " day" + (days == 1 ? "" : "s") + " ago";
        } else if (days < 365) {
            long months = days / 30;
            return months + " month" + (months == 1 ? "" : "s") + " ago";
        } else {
            long years = days / 365;
            return years + " year" + (years == 1 ? "" : "s") + " ago";
        }
    }

}
