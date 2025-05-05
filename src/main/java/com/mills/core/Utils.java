package com.mills.core;

import java.util.concurrent.TimeUnit;

public class Utils {

    public static String format(String string) {

        StringBuilder builder = new StringBuilder();

        for (String word : string.split("_")) {
            String first = word.substring(0, 1);
            String second = word.substring(1);

            builder.append(first.toUpperCase()).append(second.toLowerCase()).append(" ");
        }

        return builder.toString().trim();
    }

    public static String convertBalance(double value) {
        String[] suffixes = {
                "",     // 10^0
                "K",    // 10^3 - Thousand
                "M",    // 10^6 - Million
                "B",    // 10^9 - Billion
                "T",    // 10^12 - Trillion
                "Qa",   // 10^15 - Quadrillion
                "Qi",   // 10^18 - Quintillion
                "Sx",   // 10^21 - Sextillion
                "Sp",   // 10^24 - Septillion
                "Oc",   // 10^27 - Octillion
                "No",   // 10^30 - Nonillion
                "Dc",   // 10^33 - Decillion
                "Ud",   // 10^36 - Undecillion
                "Dd",   // 10^39 - Duodecillion
                "Td",   // 10^42 - Tredecillion
                "Qad",  // 10^45 - Quattuordecillion
                "Qid",  // 10^48 - Quindecillion
                "Sxd",  // 10^51 - Sexdecillion
                "Spd",  // 10^54 - Septendecillion
                "Ocd",  // 10^57 - Octodecillion
                "Nod",  // 10^60 - Novemdecillion
                "Vg",   // 10^63 - Vigintillion
                "Uvg",  // 10^66 - Unvigintillion
                "Dvg",  // 10^69 - Duovigintillion
                "Tvg",  // 10^72 - Tresvigintillion
                "Qavg", // 10^75 - Quattuorvigintillion
                "Qivg", // 10^78 - Quinvigintillion
                "Sxvg", // 10^81 - Sexvigintillion
                "Spvg", // 10^84 - Septenvigintillion
                "Ocvg", // 10^87 - Octovigintillion
                "Novg", // 10^90 - Novemvigintillion
                "Tg",   // 10^93 - Trigintillion
                "Qg",   // 10^120 - Quadragintillion
                "Qng",  // 10^153 - Quinquagintillion
                "Sxg",  // 10^180 - Sexagintillion
                "Sptg", // 10^210 - Septuagintillion
                "Ocg",  // 10^240 - Octogintillion
                "Nog",  // 10^270 - Nonagintillion
                "C"     // 10^303 - Centillion
        };
        int index = 0;

        while (value >= 1000 && index < suffixes.length - 1) {
            value /= 1000.0;
            index++;
        }

        String formatted = "$" + String.format("%.2f", value);

        if (formatted.endsWith(".00")) {
            formatted = formatted.substring(0, formatted.length() - 3);
        }

        return formatted + suffixes[index];
    }

    public static String formatTime(long milliseconds) {
        long seconds = milliseconds / 1000 % 60;
        long minutes = milliseconds / (1000 * 60) % 60;
        long hours = milliseconds / (1000 * 60 * 60) % 24;
        long days = milliseconds / (1000 * 60 * 60 * 24);

        StringBuilder sb = new StringBuilder();

        if (days > 0) sb.append(days).append("d").append(", ");
        if (hours > 0) sb.append(hours).append("h").append(", ");
        if (minutes > 0) sb.append(minutes).append("m").append(", ");
        if (seconds > 0 || sb.length() == 0)
            sb.append(seconds).append("s");

        String result = sb.toString();
        if (result.endsWith(", ")) {
            result = result.substring(0, result.length() - 2);
        }

        return result;
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
