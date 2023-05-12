import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;
import javax.swing.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage; 
import java.io.*;

public class Background implements GameObject {
    private int w, h;
    private Rectangle2D.Double r;
    private Color color;

    public Background (int width, int height, String s) {
        w = width;
        h = height;
        r = new Rectangle2D.Double (0, 0, w, h);
        color = Color.decode(s);
    }

    public void draw (Graphics2D g2d) {
        g2d.setColor(color);
        g2d.fill(r);
    }

    public void setColor (Color c) {
        color = c;
    }

    public double getX() {return 0;}

    public int getY() {return 0;}
}