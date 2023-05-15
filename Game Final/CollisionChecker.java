/*
Brief Description of this Class:
CollisionChecker is the class responsible for checking collisions between game objects such as sprites, hearts, projectiles, and enemies.
This also checks if a game object is out of bounds. Collision is checked using a point on the shape, such as the middle of the top part of the object.
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
import java.util.*;

public class CollisionChecker {

    public CollisionChecker () {}

    public boolean enemyCollision (Player sprite, Enemy e) {
        return ((sprite.getY() < e.getY() + e.getSize()
        && sprite.getY() > e.getY() 
        && (sprite.getX() + sprite.getSize()/2) >= e.getX() 
        && (sprite.getX() + sprite.getSize()/2) <= e.getX() + e.getSize()));

    }

    public boolean heartCollision (Player sprite, Heart h) {
        return ((sprite.getY() <= h.getY() + h.getSize() 
        && sprite.getY() + sprite.getSize()/2 >= h.getY() 
        && sprite.getY() + sprite.getSize()/2 <= h.getY() + h.getSize() 
        && (sprite.getX() + sprite.getSize()/2) >= h.getX() 
        && (sprite.getX() + sprite.getSize()/2) <= h.getX() + h.getSize()));
    }

    public boolean projectileCollision (Enemy e, Projectile p) {
        return ((p.getY() <= e.getY() + e.getSize()
        && p.getY() > e.getY() 
        && (p.getX() + p.getWidth()/2) >= e.getX() 
        && (p.getX() + p.getWidth()/2) <= e.getX() + e.getSize()));
    }

    public boolean outOfBounds_P (Projectile p, int w, int h, int platformheight) {
        return (p.getY() < 20 || p.getX() > w || p.getX()+p.getSize() < 0);
    }

    public boolean outOfBounds_E (Enemy e, int w, int h, int platformheight) {
        return (e.getY() > 580 || e.getX() > w || e.getX()+e.getSize() < 0);
    }

    public boolean outOfBounds_H (Heart h, int w, int height, int platformheight) {
        return (h.getY() >= 580 || h.getX() > w || h.getX()+h.getSize() < 0);
    }

    public boolean rightOutOfBounds (Player s, int w) {
        return (s.getX() + s.getSize() > w);
    }

    public boolean leftOutOfBounds (Player s) {
        return (s.getX() < 0);
    }

    public boolean downOutOfBounds (Player s, int h) {
        return (s.getY() + s.getSize() > h);
    }

    public boolean upOutOfBounds (Player s) {
        return (s.getY() < 0);
    }

}