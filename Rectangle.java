/*
Brief Description of this Class:
This class uses a Rectangle2D object to create a simple rectangle. This class is mainly used in creating backgrounds.  
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
