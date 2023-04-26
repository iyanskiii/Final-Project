import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class Canvas extends JComponent {
    int w, h, platformheight, score, lives, scoreLength, time;
    String scoreText;
    ArrayList<Sprite> sprites;
    ArrayList<Rectangle> environment, background;
    ArrayList<Projectile> projectiles;
    ArrayList<Enemy> enemies;
    ArrayList<Heart> hearts;
    CollisionChecker collision;

    public Canvas (int width, int height) {
        w = width;
        h = height;
        platformheight = 80;
        score = 0;
        lives = 3;
        time = 120;
        setPreferredSize(new Dimension(w, h));
        sprites = new ArrayList<Sprite>();
        background = new ArrayList<Rectangle>();
        environment = new ArrayList<Rectangle>();
        projectiles = new ArrayList<Projectile>();
        enemies = new ArrayList<Enemy> ();
        hearts = new ArrayList<Heart> ();
        background.add(new Rectangle (0, 0, w, h, "#4F5326"));
        environment.add(new Rectangle (0, h-platformheight, w, 100, "#000000"));

        addSprite(10,(h-platformheight)-80, 80);

        collision = new CollisionChecker ();

    }

    @Override
    protected void paintComponent (Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHints(rh);
        
        for (Rectangle r: background) {
            r.draw(g2d);
        }

        for (int i=0; i<sprites.size(); i++) {
            sprites.get(i).draw(g2d);
        }

        if (!projectiles.isEmpty()) {
            for (Projectile p: projectiles) {
                p.draw(g2d);
            }
        }

        if (!enemies.isEmpty()) {
            for (Enemy e: enemies) {
                e.draw(g2d);
            }
        }

        /*if (!hearts.isEmpty()) {
                for (Heart h: hearts) {
                    h.draw(g2d);
                }
        }*/

        for (Rectangle r: environment) {
            r.draw(g2d);
        }

        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font(Font.SERIF, Font.BOLD, 40));
        scoreText = ("Score: " + score);
        scoreLength = (g2d.getFontMetrics().stringWidth("Score:   "));
        g2d.drawString (scoreText, w/2-scoreLength/2, h-platformheight/2);
        g2d.drawString (("Lives: " + lives), w*3/4-(g2d.getFontMetrics().stringWidth("Lives:  "))/2, h-platformheight/2);
        g2d.drawString (("Time: " + time), w*1/4-(g2d.getFontMetrics().stringWidth("Time:   "))/2, h-platformheight/2);
    }

    public void addSprite (int x, int y, int size) {
        sprites.add (new Sprite (x, y, size));
    }

    public Sprite getSprite () {
        return sprites.get(0);
    }

    public ArrayList getEnemies () {
        return enemies;
    }

    public ArrayList getProjectiles () {
        return projectiles;
    }

    public void addProjectile (Sprite s) {
        Projectile p = new Projectile(s);
        projectiles.add(p);
    }
    
    public void shootProjectile (Sprite s) {
        if (!projectiles.isEmpty()) {
            for (Projectile p: projectiles) {
                checkCollisions(s);
                checkOutOfBounds();
                p.moveY(); 
            }
        }
    }

    public void moveEnemies (Sprite s) {
        if (!enemies.isEmpty()) {
            for (Enemy e: enemies) {
                checkCollisions(s);
                checkOutOfBounds();
                e.moveEnemy();                
            }
        }
    }

    public void moveHearts (Sprite s) {
        if (!hearts.isEmpty()) {
            for (Heart h: hearts) {
                checkCollisions(s);
                checkOutOfBounds();
                h.moveHeart();                
            }
        }
    }

    public void generateEnemy () {
        double spawnX = w * Math.random();
        Enemy e = new Enemy(spawnX);
        enemies.add(e);
    }

    public void generateLives () {
        double spawnX = w * Math.random();
        Heart e = new Heart(spawnX);
        hearts.add(e);
    }

    public void checkCollisions (Sprite s) {
        for (Enemy e: enemies) {
            if (e.isVisible()) {
                if (collision.enemyCollision(getSprite(), e)) {
                    lives--;
                    e.setVisible(false);
                    System.out.println("Colliding");
                }
            }  
        }
        for (Projectile p: projectiles) {
            for (Enemy e: enemies) {
                if (p.isVisible() && e.isVisible()) {
                    if (collision.projectileCollision(e, p)) {
                        e.setVisible(false);
                        p.setVisible(false);
                        score++;
                        System.out.println("Shot");
                    } 
                }
            }
        }
        for (Heart h: hearts) {
            if (h.isVisible()) {
                if (collision.heartCollision(s, h)) {
                    h.setVisible(false);
                    lives++;
                    System.out.println("+1 Life");
                }
            } 
        }
    }
    
    public void checkOutOfBounds () {
        for (Enemy e: enemies) {
            if (e.isVisible()) {
                if (collision.outOfBounds_E(e, h, platformheight)) {
                    e.setVisible(false); 
                }
            }
        }
        for (Projectile p: projectiles) {
            if (p.isVisible()) {
                if (collision.outOfBounds_P(p, h, platformheight)) {
                    p.setVisible(false); 
                }
            }
        }
        for (Heart heart: hearts) {
            if (heart.isVisible()) {
                if (collision.outOfBounds_H(heart, h, platformheight)) {
                    heart.setVisible(false); 
                }
            }
        }
    }

    public void Countdown () {
        time--;
    }

    public int getTime () {
        return time;
    }
}