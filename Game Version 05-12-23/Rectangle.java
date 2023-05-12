import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;
import javax.swing.*;

public class Rectangle {
    int x, y, w, h;
    Color color;
    Rectangle2D.Double r;

    public Rectangle (int x, int y, int w, int h, String c) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        color = Color.decode(c);
    }

    public void draw (Graphics2D g2d) {
        r = new Rectangle2D.Double (this.x, this.y, this.w, this.h);
        g2d.setColor(color);
        g2d.fill(r);
    }
}