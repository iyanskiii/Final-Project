/*
Brief Description of this Class:
This class uses a Rectangle object to draw a background. This class also uses a Color object to draw a colored background 
according to the theme.
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