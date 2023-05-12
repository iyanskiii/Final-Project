import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;
import javax.swing.*;

public class Projectile implements GameObject {
    private int x, y, w, h, size, speed, direction;
    private Color color;
    private boolean visible;

    public Projectile (Sprite s, int d) {
        direction = d; // 0=up; 1=down; 2=left; 3=right
        x = (int)s.getX();
        y = s.getY();
        w = s.getSize();
        h = s.getSize();
        speed = 3;
        size = 15;
        color = Color.decode("#FFFFFF");
        visible = true;
        
    }

    public void draw (Graphics2D g2d) {
        Rectangle2D.Double r = new Rectangle2D.Double (((x+w/2)), (y+size), size, size);
        g2d.setColor(color);
        if (visible) {
            g2d.fill(r);
        }
    }

    public void moveY () {
        if (direction == 0) {
            y -= speed;
        }
        if (direction == 1) {
            y+= speed;
        }
        if (direction == 2) {
            x-= speed;
        }
        if (direction == 3) {
            x += speed;
        }
    }

    public double getX () {
        return x;
    }

    public int getY () {
        return y;
    }

    public int getWidth () {
        return w;
    }

    public int getHeight () {
        return h;
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