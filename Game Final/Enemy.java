/*
Brief Description of this Class:
Enemy is the class responsible for creating the enemies that the player shoots at or avoids. Using an image file and random coordinates generated in GameCanvas, 
as well as a direction index to determine whether the enemy moves down, left, or right, this class draws an enemy accordingly.
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

public class Enemy implements GameObject {
    private int size, direction;
    private double x, y;
    private boolean visible;
    private BufferedImage img;

    public Enemy (double x, double y, BufferedImage img, int d) {
        this.x = x;
        this.y = y;
        direction = d;
        size = 50;
        this.img = img;
        visible = true;
    }

    public void draw (Graphics2D g2d) {
        if (visible) {
            g2d.drawImage(this.img, (int)this.x, (int)this.y, size, size, null);
        }
    }

    public void moveEnemy () {
        if (direction == 1) {
            y += 1;
        }
        if (direction == 2) {
            x += 1;
        }
        if (direction == 3) {
            x -= 1;
        }
    }

    public int getY () {
        return (int)y;
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