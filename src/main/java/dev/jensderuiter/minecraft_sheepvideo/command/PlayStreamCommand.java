package dev.jensderuiter.minecraft_sheepvideo.command;

import dev.jensderuiter.minecraft_sheepvideo.SheepVideoPlugin;
import dev.jensderuiter.minecraft_sheepvideo.runnable.GatherImagesRunnable;
import dev.jensderuiter.minecraft_sheepvideo.runnable.PlayVideoRunnable;
import dev.jensderuiter.minecraft_sheepvideo.util.ShellCommand;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PlayStreamCommand implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("Je bent geen speler, druiloor");
            return true;
        }

        Player player = (Player) sender;

        if (args.length != 1) {
            sender.sendMessage("Geef een URL mee, stomkop");
            return true;
        }

        String md5Url = "";

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(args[0].getBytes());
            byte[] digest = md.digest();
            BigInteger bigInt = new BigInteger(1,digest);
            md5Url = bigInt.toString(16);
        } catch (NoSuchAlgorithmException e) {
            sender.sendMessage("Oei er ging iets fout met het hashen");
            throw new RuntimeException(e);
        }

        File folder = new File(SheepVideoPlugin.plugin.getDataFolder() + "/" + md5Url);
        folder.delete();
        folder.mkdirs();

        sender.sendMessage("Stream ophalen...");

        String streamReadCmd = String.format(
                "ffmpeg -i %s -vf scale=128:72,fps=2 %s/",
                args[0], folder.getAbsolutePath()) + "%d.jpg";

        new ShellCommand(streamReadCmd);

        Location playerLocation = player.getLocation();

        sender.sendMessage("Oke, ik ga m afspelen over 3 seconden");
        new GatherImagesRunnable(folder).runTaskLaterAsynchronously(SheepVideoPlugin.plugin, 40);
        new PlayVideoRunnable(playerLocation).runTaskTimer(SheepVideoPlugin.plugin, 60, 10);

        return true;
    }
}
