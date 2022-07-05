package net.mitask.mrs.utils;

import net.mitask.mrs.models.Level;

public class LevelUtils {
    public static Level getLevel(int totalXP) {
        Level lvl = null;

        if(totalXP > 0) {
            lvl = new Level(1, totalXP, 1, 100, totalXP);
        }

        if(totalXP > 100) {
            lvl = new Level(2, totalXP-100, 1, 150, totalXP);
        }

        if(totalXP > 250) {
            lvl = new Level(3, totalXP-250, 2, 200, totalXP);
        }

        if(totalXP > 400) {
            lvl = new Level(4, totalXP-400, 2, 300, totalXP);
        }

        if(totalXP > 500) {
            lvl = new Level(5, totalXP-500, 3, 400, totalXP);
        }

        return lvl;
    }
}
