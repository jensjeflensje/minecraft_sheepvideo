package dev.jensderuiter.minecraft_sheepvideo.runnable;

import dev.jensderuiter.minecraft_sheepvideo.SheepVideoPlugin;
import dev.jensderuiter.minecraft_sheepvideo.util.ColorUtil;
import dev.jensderuiter.minecraft_sheepvideo.util.ImageFrame;
import org.bukkit.*;
import org.bukkit.Color;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Sheep;
import org.bukkit.scheduler.BukkitRunnable;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Collection;

public class PlayVideoRunnable extends BukkitRunnable {

    // 128x72
    public int WIDTH = 128;
    public int HEIGHT = 72;
    public Location location;
    public World world;

    public PlayVideoRunnable(Location location) {
        this.location = location;
        this.world = location.getWorld();
    }

    @Override
    public void run() {
        ImageFrame image = SheepVideoPlugin.frameQueue.poll();
        if (image == null) {
            Bukkit.getLogger().info("No images left, stopping playback.");
            this.cancel();
            return;
        };
        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                int  clr   = image.image.getRGB(x, y);
                int  red   = (clr & 0x00ff0000) >> 16;
                int  green = (clr & 0x0000ff00) >> 8;
                int  blue  =  clr & 0x000000ff;

                // y on z because we don't want it to go up
                Location blockLocation = this.location.clone().add(x, -10, y);

                Collection<Entity> entities = this.world.getNearbyEntities(blockLocation, 1, 10, 1);

                entities.forEach((entity -> {
                    if (!(entity instanceof Sheep)) return;
                    Sheep sheep = (Sheep) entity;
                    sheep.setColor(ColorUtil.fromRGB(red, green, blue));
                }));

            }
        }
    }
    
}
