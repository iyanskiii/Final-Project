/*
Brief Description of this Class:
Heart is a class responsible for drawing a heart image that the player collides with in order to gain an additional life.
This class does this using an image file.
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

public class Heart implements GameObject {
    private int y, size;
    private double x;
    private boolean visible;
    private BufferedImage img;

    public Heart(double x, BufferedImage img) {
        this.x = x;
        y = 1;
        size = 50;
        this.img = img;
        visible = true;
    }

    public void draw(Graphics2D g2d) {
        if (visible) {
            g2d.drawImage(this.img, (int) this.x, y, size, size, null);
        }
    }

    public void moveHeart() {
        y += 1;
    }

    public int getY() {
        return y;
    }

    public double getX() {
        return this.x;
    }

    public int getSize() {
        return size;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean b) {
        visible = b;
    }

}