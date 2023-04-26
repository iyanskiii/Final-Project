import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;
import javax.swing.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage; 
import java.io.*;

public class Enemy {
    private int y, size;
    private double x;
    private boolean visible;

    public Enemy (double x) {
        this.x = x;
        y = 1;
        size = 40;
        visible = true;
    }

    public void draw (Graphics2D g2d) {
        try {
            if (visible) {
                BufferedImage img = ImageIO.read(new File ("enemy.png"));
                g2d.drawImage(img, (int)this.x, y, size, size, null);
            }
        }
        catch (IOException e) {}
    }

    public void moveEnemy () {
        y += 1;
    }

    public int getY () {
        return y;
    }

    public double getX () {
        return this.x;
    }
    
    public int getSize () {
        return size;
    }

    public void setVisible (boolean b) {
        visible = b;
    }

    public boolean isVisible () {
        return visible;
    }

}