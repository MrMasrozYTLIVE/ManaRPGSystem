package net.mitask.mrs.spells;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import net.mitask.mrs.ManaRPGSystem;
import net.mitask.mrs.models.RPGUser;
import org.bson.Document;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permission;
import org.jetbrains.annotations.NotNull;

import java.util.*;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.combine;
import static com.mongodb.client.model.Updates.set;

public class Spell {
    String name;
    Permission permission;
    HashMap<String, Long> cooldown = new HashMap<String, Long>();
    int manaUsage, xpGiving;

    public Spell(String name, int manaUsage, int xpGiving) {
        this.name = name;
        this.manaUsage = manaUsage;
        this.xpGiving = xpGiving;
    }

    public void setPermission(Permission permission) {
        this.permission = permission;
    }

    public void onExecute(CommandSender sender, String[] args) {
        MongoClient mongoClient = ManaRPGSystem.getMongoClient();
        MongoDatabase database = mongoClient.getDatabase("RPG");
        MongoCollection<Document> collection = database.getCollection("users");
        Document user = collection.find(eq("nickname", sender.getName())).first();
        RPGUser rpgUser = null;

        for(RPGUser ru : ManaRPGSystem.rpgUsers) {
            if(sender.getName().equals(ru.getNickname())) {
                rpgUser = ru;
                break;
            }
        }

        rpgUser.setMana(rpgUser.getMana() - getManaUsage());
        rpgUser.getLvl().addXp(getXpGiving());
    }

    public String getName() {
        return name;
    }
    public @NotNull Permission getPermission() {
        return permission;
    }
    public HashMap<String, Long> getCooldown() {
        return cooldown;
    }
    public int getManaUsage() {
        return manaUsage;
    }
    public int getXpGiving() {
        return xpGiving;
    }
}