package com.mills.core.PlaytimeUtils;

import org.bukkit.OfflinePlayer;
import org.bukkit.Statistic;

public class PlaytimeUtils {

    public static long getPlaytimeMilis(OfflinePlayer player) {
        int ticks = player.getStatistic(Statistic.PLAY_ONE_MINUTE);
        return ticks * 50L;
    }
}
