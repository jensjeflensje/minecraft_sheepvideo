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

public class PlayVideoCommand implements CommandExecutor {


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
        folder.mkdirs();

        sender.sendMessage("Video downloaden...");

        String downloadCmd = String.format(
                "yt-dlp -f wv -o %s/video.mp4 %s",
                folder.getAbsolutePath(), args[0]);

        ShellCommand downloadCmdObj = new ShellCommand(downloadCmd);

        Location playerLocation = player.getLocation();

        new BukkitRunnable() {
            @Override
            public void run() {
                int statusCode = downloadCmdObj.waitFor();
                if (statusCode == 0) {
                    sender.sendMessage("Video gedownload! Verwerken...");

                    String processCmd = String.format(
                            "ffmpeg -i %s/video.mp4 -vf scale=128:72,fps=20 %s/",
                            folder.getAbsolutePath(), folder.getAbsolutePath()) + "%d.jpg";
                    ShellCommand processCmdObj = new ShellCommand(processCmd);
                    if (processCmdObj.waitFor() != 0) {
                        sender.sendMessage("Oei, foutje bij het verwerken");
                        return;
                    }
                    sender.sendMessage("Oke, ik ga m nu afspelen");
                    new GatherImagesRunnable(folder).runTaskAsynchronously(SheepVideoPlugin.plugin);
                    new PlayVideoRunnable(playerLocation).runTaskTimer(SheepVideoPlugin.plugin, 5, 1);
                } else {
                    sender.sendMessage("Oei, status code " + statusCode);
                }
            }
        }.runTaskAsynchronously(SheepVideoPlugin.plugin);


        return true;
    }
}
