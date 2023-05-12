import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;
import javax.swing.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage; 
import java.io.*;

public class Sprite implements GameObject {
    private int x, y, size;
    private String c;
    private Rectangle2D.Double r;
    private Color color;
    private BufferedImage img;

    public Sprite (BufferedImage img, int x, int y, int size) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.img = img;
    }

    public void draw (Graphics2D g2d) {
        g2d.drawImage(img, this.x, this.y, this.size, this.size, null);
    }

    public void moveX (int horizontal) {
        this.x += horizontal;
    }

    public void moveY (int vertical) {
        this.y += vertical;
    }

    public void setX (int location) {
        this.x = location;
    }

    public void setY (int location) {
        this.y = location;
    }

    public double getX () {
        return this.x;
    }

    public int getY () {
        return this.y;
    }

    public int getSize () {
        return this.size;
    }

    public void setImage (BufferedImage i) {
        this.img = i;
    }

}