import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;
import javax.swing.*;

public class Projectile {
    int x, y, w, h;
    Color color;
    boolean visible;

    public Projectile (Sprite s) {
        x = s.getX();
        y = s.getY();
        w = s.getWidth();
        h = s.getHeight();
        color = Color.decode("#FFFFFF");
        visible = true;
        
    }

    public void draw (Graphics2D g2d) {
        Rectangle2D.Double r = new Rectangle2D.Double (((x+w/2)-5), (y-15), 10, 20);
        g2d.setColor(color);
        if (visible) {
            g2d.fill(r);
        }
        
    }

    public void moveY () {
        y -= 2;
    }

    public int getX () {
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

    public void setVisible (boolean b) {
        visible = b;
    }

    public boolean isVisible () {
        return visible;
    }

}