package dev.jensderuiter.minecraft_sheepvideo.util;

import org.bukkit.Bukkit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ShellCommand {

    public Process process;
    public String command;

    public ShellCommand(String command) {
        this.command = command;
        Bukkit.getLogger().info("Executing: " + command);
        try {
            process = Runtime.getRuntime().exec(command);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public int waitFor() {
        int statusCode = 0;
        try {
            statusCode = process.waitFor();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        if (statusCode != 0) {
            BufferedReader stdInput = new BufferedReader(new
                    InputStreamReader(process.getErrorStream()));
            StringBuilder outputBuilder = new StringBuilder();
            String s;
            while (true) {
                try {
                    if ((s = stdInput.readLine()) == null) break;
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                outputBuilder.append(s);
            }
            Bukkit.getLogger().info(String.format(
                    "Command '%s' failed with error (status %d): '%s'",
                    this.command,
                    statusCode,
                    outputBuilder.toString()
            ));
        }
        return statusCode;
    }

}
