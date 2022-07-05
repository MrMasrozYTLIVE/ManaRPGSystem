package net.mitask.mrs.utils;

import net.mitask.mrs.ManaRPGSystem;
import net.mitask.mrs.models.RPGUser;
import org.bukkit.Bukkit;
import org.bukkit.boss.*;
import org.bukkit.entity.Player;

public class BossBarUtils {
    private int taskID = -1;
    private ManaRPGSystem plugin;
    private BossBar bar;

    public BossBarUtils(ManaRPGSystem plugin) {
        this.plugin = plugin;
    }

    public void addPlayer(Player player) {
        bar.addPlayer(player);
    }
    public void removePlayer(Player player) {
        bar.removePlayer(player);
    }

    public BossBar getBar() {
        return bar;
    }

    public void createBar(Player player) {
        bar = Bukkit.createBossBar("Как странно... Это не должно так быть...", BarColor.BLUE, BarStyle.SEGMENTED_10);
        bar.setVisible(true);
        addPlayer(player);
        cast(player);
    }

    public void removeBar(Player player) {
        bar.removeAll();
        bar = null;
        Bukkit.getScheduler().cancelTask(taskID);
    }

    public void cast(Player player) {
        taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
            @Override
            public void run() {
                RPGUser ru = getUser(player.getName());
                double mana = ru.getMana() / ((double) ru.getMaxMana());
                String title = "Мана: " + ru.getMana() + "/" + ru.getMaxMana();

                if(ru.getMana() > (ru.getMaxMana() / 2)) bar.setColor(BarColor.BLUE);
                if(ru.getMana() < (ru.getMaxMana() / 2)) bar.setColor(BarColor.YELLOW);
                if(ru.getMana() < (ru.getMaxMana() / 4)) bar.setColor(BarColor.RED);

                bar.setTitle(title);
                bar.setProgress(mana);
            }
        }, 0, 4L);
    }

    private RPGUser getUser(String name) {
        for(RPGUser ru : ManaRPGSystem.rpgUsers) {
            if(ru.getNickname().equals(name)) {
                return ru;
            }
        }

        return null;
    }
}
