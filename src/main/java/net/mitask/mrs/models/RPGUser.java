package net.mitask.mrs.models;

import net.mitask.mrs.ManaRPGSystem;
import net.mitask.mrs.utils.BossBarUtils;
import org.bukkit.Bukkit;

public class RPGUser {
    private final String nickname;
    private int mana, maxMana;
    private int manaRegen = 1;
    public BossBarUtils bar;
    private Level lvl;

    public RPGUser(String nn, int mana, Level level) {
        this.nickname = nn;
        this.mana = mana;

        this.maxMana = level.getManaLimit();
        this.manaRegen = level.getManaRegen();
        this.lvl = level;

        bar = new BossBarUtils(ManaRPGSystem.instance);
        bar.createBar(Bukkit.getPlayer(nn));
    }

    public String getNickname() {
        return nickname;
    }

    public int getMana() {
        return mana;
    }
    public int getMaxMana() {
        return maxMana;
    }
    public int getManaRegen() {
        return manaRegen;
    }
    public Level getLvl() {
        return lvl;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }
    public void setMaxMana(int maxMana) {
        this.maxMana = maxMana;
    }
    public void setManaRegen(int manaRegen) {
        this.manaRegen = manaRegen;
    }
}
