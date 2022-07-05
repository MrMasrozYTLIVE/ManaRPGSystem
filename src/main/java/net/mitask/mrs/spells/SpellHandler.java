package net.mitask.mrs.spells;

import net.mitask.mrs.ManaRPGSystem;
import net.mitask.mrs.models.RPGUser;
import net.mitask.mrs.spells.impl.*;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Objects;

public class SpellHandler implements CommandExecutor {
    ArrayList<Spell> spells = new ArrayList<Spell>();

    public void init() {
        spells.add(new TestSpell());
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof ConsoleCommandSender) return true;

        for(Spell spell : spells) {
            if(Objects.equals(spell.getName(), args[0])) {
                boolean perm = spell.getPermission() == null || sender.hasPermission(spell.getPermission());
                if(perm) {
                    RPGUser ru = getUser(sender.getName());
                    if(ru == null) {
                        sendMessage(sender,"Не удалось найти вас среди пользователей! Пожалуйста, перезайдите на сервер!");
                        return true;
                    }

                    if(ru.getMana() < spell.getManaUsage()) {
                        sendMessage(sender,"У вас недостаточно маны! У вас есть: " + ru.getMana()
                                + ", нужно маны: " + spell.getManaUsage());
                        return true;
                    }

                    spell.onExecute(sender, args);
                    return true;
                } else {
                    sendMessage(sender, "Вы не можете использовать данное заклинание.");
                    return true;
                }
            }
        }
        return true;
    }

    private void sendMessage(CommandSender sender, String message) {
        sender.sendMessage(ChatColor.GRAY+"["+ChatColor.GOLD+"MRS"+ChatColor.GRAY+"] "+ChatColor.GOLD+message);
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
