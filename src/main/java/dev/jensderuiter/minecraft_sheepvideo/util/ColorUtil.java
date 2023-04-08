package dev.jensderuiter.minecraft_sheepvideo.util;

import org.bukkit.DyeColor;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class ColorUtil {

    private static class ColorSet<R, G, B> {
        R red = null;
        G green = null;
        B blue = null;

        ColorSet(R red, G green, B blue) {
            this.red = red;
            this.green = green;
            this.blue = blue;
        }

        public R getRed() {
            return red;
        }

        public G getGreen() {
            return green;
        }

        public B getBlue() {
            return blue;
        }

    }

    public static DyeColor fromRGB(int r, int g, int b) {
        TreeMap<Integer, DyeColor> closest = new TreeMap<Integer, DyeColor>();
        for (DyeColor color : DyeColor.values()) {
            int red = Math.abs(r - color.getColor().getRed());
            int green = Math.abs(g - color.getColor().getGreen());
            int blue = Math.abs(b - color.getColor().getBlue());
            closest.put(red + green + blue, color);
        }
        return closest.firstEntry().getValue();
    }

}