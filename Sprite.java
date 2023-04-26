import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;
import javax.swing.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage; 
import java.io.*;

public class Sprite {
    private int x, y, size;
    private String c;
    private Rectangle2D.Double r;
    private Color color;

    public Sprite (int x, int y, int size) {
        this.x = x;
        this.y = y;
        this.size = size;
    }

    public void draw (Graphics2D g2d) {
        try {
            BufferedImage img = ImageIO.read(new File ("tank.png"));
            g2d.drawImage(img, this.x, this.y, this.size, this.size, null);
        }
        catch (IOException e) {}
    }

    public void moveSpriteX(int horizontal) {
        this.x += horizontal;
    }

    public void moveSpriteY(int vertical) {
        this.y += vertical;
    }

    public int getX () {
        return this.x;
    }

    public int getY () {
        return this.y;
    }

    public int getWidth () {
        return this.size;
    }

    public int getHeight () {
        return this.size;
    }
        
}