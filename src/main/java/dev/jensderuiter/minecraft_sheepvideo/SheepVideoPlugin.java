package dev.jensderuiter.minecraft_sheepvideo;

import dev.jensderuiter.minecraft_sheepvideo.command.PlayStreamCommand;
import dev.jensderuiter.minecraft_sheepvideo.command.PlayVideoCommand;
import dev.jensderuiter.minecraft_sheepvideo.command.SpawnSheepCommand;
import dev.jensderuiter.minecraft_sheepvideo.util.ImageFrame;
import org.bukkit.plugin.java.JavaPlugin;

import java.awt.image.BufferedImage;
import java.util.Queue;
import java.util.concurrent.PriorityBlockingQueue;

public final class SheepVideoPlugin extends JavaPlugin {

    public static SheepVideoPlugin plugin;
    public static Queue<ImageFrame> frameQueue = new PriorityBlockingQueue<>();

    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;

        getCommand("playvideo").setExecutor(new PlayVideoCommand());
        getCommand("playstream").setExecutor(new PlayStreamCommand());
        getCommand("spawnsheep").setExecutor(new SpawnSheepCommand());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
