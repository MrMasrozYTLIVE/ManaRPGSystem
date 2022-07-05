package net.mitask.mrs.listeners;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import net.mitask.mrs.ManaRPGSystem;
import net.mitask.mrs.models.RPGUser;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.combine;
import static com.mongodb.client.model.Updates.set;

public class OnPlayerLeave implements Listener {
    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        MongoClient mongoClient = ManaRPGSystem.getMongoClient();
        MongoDatabase database = mongoClient.getDatabase("RPG");
        MongoCollection<Document> collection = database.getCollection("users");
        Document user = collection.find(eq("nickname", event.getPlayer().getName())).first();
        RPGUser rpgUser = null;

        for(RPGUser ru : ManaRPGSystem.rpgUsers) {
            if(event.getPlayer().getName().equals(ru.getNickname())) {
                rpgUser = ru;
                break;
            }
        }

        int mana = rpgUser == null ? 100 : rpgUser.getMana();
        int totalXP = rpgUser == null ? 0 : rpgUser.getLvl().getTotalXP();
        collection.updateOne(eq("nickname", event.getPlayer().getName()),
                combine(set("mana", mana), set("totalXP", totalXP)));

        if(rpgUser != null) {
            rpgUser.bar.removeBar(Bukkit.getPlayer(rpgUser.getNickname()));
            ManaRPGSystem.rpgUsers.remove(rpgUser);
        } else {
            ManaRPGSystem.logger.error("RPGUser somehow null... How strange");
        }
    }
}
