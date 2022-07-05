package net.mitask.mrs;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import net.mitask.mrs.listeners.*;
import net.mitask.mrs.models.RPGUser;
import net.mitask.mrs.spells.Spell;
import net.mitask.mrs.spells.SpellHandler;
import net.mitask.mrs.utils.BossBarUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

public final class ManaRPGSystem extends JavaPlugin {
    public static ArrayList<RPGUser> rpgUsers = new ArrayList<RPGUser>();
    public static MongoClient mongoClient;
    public static Logger logger = LoggerFactory.getLogger("MRS");
    public static SpellHandler spellHandler;
    public static ManaRPGSystem instance;

    @Override
    public void onEnable() {
        instance = this;

        logger.info("Loading Database...");
        mongoClient = new MongoClient(new MongoClientURI("mongodb://MiTask:32167@node2.mitask.tech:25600/RPG?authSource=admin&retryWrites=true"));

        logger.info("Loading Listeners...");
        getServer().getPluginManager().registerEvents(new OnPlayerJoin(), this);
        getServer().getPluginManager().registerEvents(new OnPlayerLeave(), this);

        logger.info("Loading SpellHandler...");
        spellHandler = new SpellHandler();
        spellHandler.init();

        logger.info("Loading Spells...");
        this.getCommand("spell").setExecutor(spellHandler);

        logger.info("Loading Mana Regen Schedule");
        long time = (long) 20;
        new BukkitRunnable() {
            @Override
            public void run() {
                if(rpgUsers.size() < 1) return;
                for(RPGUser ru : rpgUsers) {
                    if(ru.getMana() < ru.getMaxMana()) {
                        ru.setMana(ru.getMana() + ru.getManaRegen());
                        /*getServer().getPlayer(ru.getNickname()).sendMessage(
                                ChatColor.GRAY + "[" + ChatColor.GOLD + "MRS" + ChatColor.GRAY + "] " + ChatColor.GOLD + "+1 мана"
                        );*/
                    }
                }
            }
        }.runTaskTimerAsynchronously(this, 0, time);

        logger.info("Plugin loaded.");
    }

    @Override
    public void onDisable() {
        logger.info("Plugin unloaded!");
    }

    public static MongoClient getMongoClient() {
        return mongoClient;
    }
}
