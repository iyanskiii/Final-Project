/*
Brief Description of this Class:
Player is the class responsible for drawing the player sprite. By using an image file, this class handles the movement of the player sprite as well.
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

public class Player implements GameObject {
    private int x, y, size;
    private String c;
    private Rectangle2D.Double r;
    private Color color;
    private BufferedImage img;

    public Player (BufferedImage img, int x, int y, int size) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.img = img;
    }

    public void draw (Graphics2D g2d) {
        g2d.drawImage(img, this.x, this.y, this.size, this.size, null);
    }

    public void moveX (int horizontal) {
        this.x += horizontal;
    }

    public void moveY (int vertical) {
        this.y += vertical;
    }

    public void setX (int location) {
        this.x = location;
    }

    public void setY (int location) {
        this.y = location;
    }

    public double getX () {
        return this.x;
    }

    public int getY () {
        return this.y;
    }

    public int getSize () {
        return this.size;
    }

    public void setImage (BufferedImage i) {
        this.img = i;
    }

}