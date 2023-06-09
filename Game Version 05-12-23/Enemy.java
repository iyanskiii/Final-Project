import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;
import javax.swing.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage; 
import java.io.*;

public class Enemy implements GameObject {
    private int y, size;
    private double x;
    private boolean visible;
    private BufferedImage img;

    public Enemy (double x, BufferedImage img) {
        this.x = x;
        y = 1;
        size = 50;
        this.img = img;
        visible = true;
    }

    public void draw (Graphics2D g2d) {
        if (visible) {
            g2d.drawImage(this.img, (int)this.x, y, size, size, null);
        }
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