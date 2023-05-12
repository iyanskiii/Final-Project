import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;
import javax.swing.*;

public interface GameObject {
    void draw (Graphics2D g2d);
    double getX ();
    int getY ();
}