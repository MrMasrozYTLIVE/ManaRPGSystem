package net.mitask.mrs.listeners;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import net.mitask.mrs.ManaRPGSystem;
import net.mitask.mrs.models.Level;
import net.mitask.mrs.models.RPGUser;
import net.mitask.mrs.utils.LevelUtils;
import org.bson.Document;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import static com.mongodb.client.model.Filters.eq;

public class OnPlayerJoin implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        MongoClient mongoClient = ManaRPGSystem.getMongoClient();
        MongoDatabase database = mongoClient.getDatabase("RPG");
        MongoCollection<Document> collection = database.getCollection("users");
        String nickname = event.getPlayer().getName();
        Document user = collection.find(eq("nickname", nickname)).first();

        if(user == null) {
            Document doc = new Document("nickname", nickname)
                    .append("mana", 100)
                    .append("totalXP", 0);
            collection.insertOne(doc);

            user = collection.find(eq("nickname", nickname)).first();
        }

        assert user != null;
        Level lvl = LevelUtils.getLevel(user.getInteger("totalXP"));
        RPGUser rpgUser = new RPGUser(nickname, user.getInteger("mana"), lvl);

        ManaRPGSystem.rpgUsers.add(rpgUser);
    }
}
