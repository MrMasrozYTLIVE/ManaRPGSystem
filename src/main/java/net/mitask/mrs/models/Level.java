package net.mitask.mrs.models;

import net.mitask.mrs.utils.LevelUtils;

public class Level {
    private int lvl, xp, manaRegen, manaLimit, totalXP;

    public Level(int lvl, int xp, int manaRegen, int manaLimit, int totalXP) {
        this.lvl = lvl;
        this.xp = xp;
        this.manaRegen = manaRegen;
        this.manaLimit = manaLimit;
        this.totalXP = totalXP;
    }

    public int getLvl() {
        return lvl;
    }
    public int getManaRegen() {
        return manaRegen;
    }
    public int getManaLimit() {
        return manaLimit;
    }
    public int getXp() {
        return xp;
    }
    public int getTotalXP() {
        return totalXP;
    }

    public void setLvl(int lvl) {
        this.lvl = lvl;
    }
    public void setManaRegen(int manaRegen) {
        this.manaRegen = manaRegen;
    }
    public void setManaLimit(int manaLimit) {
        this.manaLimit = manaLimit;
    }
    public void setXp(int xp) {
        this.xp = xp;
    }

    public void addXp(int xp) {
        totalXP = this.xp + xp;
        Level lvl = LevelUtils.getLevel(totalXP);

        this.setXp(lvl.getXp());
        this.setLvl(lvl.getLvl());
        this.setManaLimit(lvl.getManaLimit());
        this.setManaRegen(lvl.getManaRegen());
    }
}
