package dev.jensderuiter.minecraft_sheepvideo.runnable;

import dev.jensderuiter.minecraft_sheepvideo.SheepVideoPlugin;
import dev.jensderuiter.minecraft_sheepvideo.util.ImageFrame;
import org.bukkit.Bukkit;
import org.bukkit.entity.Sheep;
import org.bukkit.scheduler.BukkitRunnable;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

public class GatherImagesRunnable extends BukkitRunnable {

    public File folder;
    public int frameCount;

    public GatherImagesRunnable(File folder) {
        this.folder = folder;
        this.frameCount = 0;
    }

    @Override
    public void run() {
        while (true) {
            if (SheepVideoPlugin.frameQueue.size() < 40) {
                File frameFile = new File(this.folder.getAbsolutePath(), ++this.frameCount + ".jpg");
                if (!frameFile.exists()) {
                    Bukkit.getLogger().info("File with count " + this.frameCount + " doesn't exist, stopping.");
                    return;
                }
                BufferedImage image = null;
                try {
                    image = ImageIO.read(frameFile);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                SheepVideoPlugin.frameQueue.add(new ImageFrame(this.frameCount, image));
            }
        }
    }
}
