package dev.jensderuiter.minecraft_sheepvideo.util;

import java.awt.image.BufferedImage;

public class ImageFrame implements Comparable<ImageFrame> {
    public int id;
    public BufferedImage image;

    public ImageFrame(int id, BufferedImage image) {
        this.id = id;
        this.image = image;
    }

    @Override
    public int compareTo(ImageFrame o) {
        return o.id == this.id ? 0 : 1;
    }
}
