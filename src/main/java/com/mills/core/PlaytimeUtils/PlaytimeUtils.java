package com.mills.core.PlaytimeUtils;

import org.bukkit.OfflinePlayer;
import org.bukkit.Statistic;

public class PlaytimeUtils {

    public static String formatPlaytime(long milliseconds) {
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

    public static long getPlaytimeMilis(OfflinePlayer player) {
        int ticks = player.getStatistic(Statistic.PLAY_ONE_MINUTE);
        return ticks * 50L;
    }
}
