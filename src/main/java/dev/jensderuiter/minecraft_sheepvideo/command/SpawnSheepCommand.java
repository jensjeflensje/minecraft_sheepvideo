package dev.jensderuiter.minecraft_sheepvideo.command;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Sheep;

public class SpawnSheepCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Je bent geen speler, druiloor");
            return true;
        }

        Player player = (Player) sender;

        Location location = player.getLocation();

        for (int i = 0; i < 1000; i++) {
            location.getWorld().spawn(location, Sheep.class);
        }
        sender.sendMessage("Schapen in overvloed");
        return true;
    }
}
