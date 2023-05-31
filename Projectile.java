/*
Brief Description of this Class:
Projectile is the class responsible for creating the projectiles that the player shoots. Consisted of a Rectangle2D shape, this class
uses a Player object to determine the positon the projectile is created. This projectile is them moved in the GameCanvas using the move methods in this class.
An integer variable is also used to determine whether the projectile moves up, down, left, or right depending on the player's rotation. 
*/
/**
CSCI22 Final Project - Animated Scene
@author Ronald Francis Bautista & Ian Roque Ferol
@version May 15, 2023
**/
/*
We have not discussed the Java language code in our program 
with anyone other than our instructor/s or the teaching assistants 
assigned to this course.
We have not used Java language code obtained from another student, 
or any other unauthorized source, either modified or unmodified.
If any Java language code or documentation used in our program 
was obtained from another source, such as a textbook or website, 
that has been clearly noted with a proper citation in the comments 
of our program.
*/
/*
Certificate of Authorship:
We hereby certify that the submission described in this document abides by 
the principles stipulated in the DISCS Academic Integrity Policy document.
We further certify that we are the authors of this submission and that any assistance 
We received in its preparation is fully acknowledged and disclosed in the documentation.
*/

import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;
import javax.swing.*;

public class Projectile implements GameObject {
    private int x, y, w, h, size, speed, direction;
    private Color color;
    private boolean visible;

    public Projectile (Player s, int d) {
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
