package net.mitask.mrs.spells.impl;

import net.mitask.mrs.spells.Spell;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class TestSpell extends Spell {
    public TestSpell() {
        super("test", 10,  50);
    }

    public void onExecute(CommandSender sender, String[] args) {
        super.onExecute(sender, args);

        sender.sendMessage("test");
    }
}