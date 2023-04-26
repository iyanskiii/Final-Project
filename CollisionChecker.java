import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class CollisionChecker {

    public CollisionChecker () {}

    public boolean enemyCollision (Sprite sprite, Enemy e) {
        return ((sprite.getY() == e.getY() + e.getSize() && (sprite.getX() + sprite.getWidth()/2) >= e.getX() && (sprite.getX() + sprite.getWidth()/2) <= e.getX() + e.getSize())
        || (sprite.getX() == e.getX() + e.getSize()) 
        || (sprite.getX() + sprite.getWidth() == e.getX()));
    }

    public boolean heartCollision (Sprite sprite, Heart h) {
        return ((sprite.getY() == h.getY() + h.getSize() && (sprite.getX() + sprite.getWidth()/2) >= h.getX() && (sprite.getX() + sprite.getWidth()/2) <= h.getX() + h.getSize())
        || (sprite.getX() == h.getX() + h.getSize()) 
        || (sprite.getX() + sprite.getWidth() == h.getX()));
    }

    public boolean projectileCollision (Enemy e, Projectile p) {
        return ((p.getY() == e.getY() + e.getSize() && (p.getX() + p.getWidth()/2) >= e.getX() && (p.getX() + p.getWidth()/2) <= e.getX() + e.getSize())
        || (p.getX() == e.getX() + e.getSize()) 
        || (p.getX() + p.getWidth() == e.getX()));
    }

    public boolean outOfBounds_P (Projectile p, int h, int platformheight) {
        return (p.getY() <= 20);
    }

    public boolean outOfBounds_E (Enemy e, int h, int platformheight) {
        return (e.getY() >= 580);
    }

    public boolean outOfBounds_H (Heart h, int height, int platformheight) {
        return (h.getY() >= 580);
    }

}