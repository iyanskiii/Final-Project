/*
Brief Description of this Class:
GameCanvas is mainly responsible for drawing the graphics of the game itself. This drays the sprites, projectiles, enemies, and sprites.
This class also contains methods for moving the game objects, which are used in the timers in GameFrame. 
This class is also responsible for setting the theme for the game, which is mainly done using an ArrayList of image files.
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
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage; 
import java.io.*;

public class GameCanvas extends JComponent {
    public int w, h;
    private int platformheight, score, oppScore, lives, scoreLength, time, index, angle;
    private long seed;
    private Random random;
    private double spawnX;
    private String scoreText, oppScoreText, theme;
    private ArrayList<Player> sprites;
    private ArrayList<Rectangle> environment;
    private ArrayList<Background> background;
    private ArrayList<Projectile> projectiles;
    private ArrayList<Enemy> enemies;
    private ArrayList<Heart> hearts;
    private ArrayList<Background> backgrounds;
    private ArrayList<Color> colors;
    private CollisionChecker collision;
    private BufferedImage sprite, enemy, heart;
    private ArrayList<BufferedImage> s_Images, e_Images;
    private AffineTransform reset;

    public GameCanvas (int width, int height, long s) {
        w = width;
        h = height;
        s = seed;
        random = new Random (seed);
        angle = 0;
        platformheight = 80;
        score = 0;
        oppScore = 0;
        lives = 3;
        time = 120;
        setPreferredSize(new Dimension(w, h));
        sprites = new ArrayList<Player>();
        background = new ArrayList<Background>();
        environment = new ArrayList<Rectangle>();
        projectiles = new ArrayList<Projectile>();
        enemies = new ArrayList<Enemy> ();
        hearts = new ArrayList<Heart> ();
        colors = new ArrayList<Color> ();
        backgrounds = new ArrayList<Background> ();
        s_Images = new ArrayList<BufferedImage> ();
        e_Images = new ArrayList<BufferedImage> ();
        
        background.add(new Background (w, h, "#4F5326"));
        environment.add(new Rectangle (0, h-platformheight, w, 100, "#000000"));

        try {
            s_Images.add(ImageIO.read(getClass().getResourceAsStream("assets/tank.png")));
            s_Images.add(ImageIO.read(getClass().getResourceAsStream("assets/wizard.png")));
            s_Images.add(ImageIO.read(getClass().getResourceAsStream("assets/mfalcon.png")));
            s_Images.add(ImageIO.read(getClass().getResourceAsStream("assets/beachball.png")));

            e_Images.add(ImageIO.read(getClass().getResourceAsStream("assets/mandalorian.png")));
            e_Images.add(ImageIO.read(getClass().getResourceAsStream("assets/ghost.png")));
            e_Images.add(ImageIO.read(getClass().getResourceAsStream("assets/tiefighter.png")));
            e_Images.add(ImageIO.read(getClass().getResourceAsStream("assets/crab.png")));
        }
        catch (IOException e) {}

        colors.add(Color.decode("#4F5326"));
        colors.add(Color.decode("#6B4188"));
        colors.add(Color.decode("#212F3C"));
        colors.add(Color.decode("#BF9867"));

                
        try {
            heart = ImageIO.read(getClass().getResourceAsStream("assets/heart.png"));
        }
        catch (IOException e) {}

        sprites.add(new Player(s_Images.get(0), w/2,(h/2-platformheight), 80));

        collision = new CollisionChecker ();

    }

    @Override
    protected void paintComponent (Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHints(rh);

        setBackground(index);
        
        for (Background r: background) {
            r.draw(g2d);
        }
        
        setSprite(index);

        reset = g2d.getTransform();
        g2d.rotate (Math.toRadians(angle), (getSprite(0).getX()+(getSprite(0).getSize()/2)), (getSprite(0).getY()+getSprite(0).getSize()/2));

        getSprite(0).draw(g2d);
        g2d.setTransform (reset);

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

        if (!hearts.isEmpty()) {
                for (Heart h: hearts) {
                    h.draw(g2d);
                }
        }

        for (Rectangle r: environment) {
            r.draw(g2d);
        }

        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Dialog", Font.BOLD, 25));
        scoreText = ("Score: " + score);
        scoreLength = (g2d.getFontMetrics().stringWidth("Score:  "));
        g2d.drawString (scoreText, w*3/10-scoreLength/2, h-platformheight/2);
        oppScoreText = ("Opponent's Score: " + oppScore);
        scoreLength = (g2d.getFontMetrics().stringWidth("Opponent's Score:  "));
        g2d.drawString (oppScoreText, w*6/10-scoreLength/2, h-platformheight/2);
        g2d.drawString (("Lives: " + lives), w*9/10-(g2d.getFontMetrics().stringWidth("Lives:  "))/2, h-platformheight/2);
        g2d.drawString (("Time: " + time), w*1/10-(g2d.getFontMetrics().stringWidth("Time:   "))/2, h-platformheight/2);
    }

    public Player getSprite (int i) {
        return sprites.get(i);
    }

    public void setSprite (int i) {
        getSprite(0).setImage(s_Images.get(i));
    }

    public void setBackground (int i) {
        background.get(0).setColor(colors.get(i));
    }

    public ArrayList getEnemies () {
        return enemies;
    }

    public ArrayList getProjectiles () {
        return projectiles;
    }

    public void addProjectile (Player s, int d) {
        Projectile p = new Projectile(s, d);
        projectiles.add(p);
    }
    
    public void shootProjectile (Player s) {
        if (!projectiles.isEmpty()) {
            for (Projectile p: projectiles) {
                checkCollisions(s);
                checkOutOfBounds();
                p.moveY(); 
            }
        }
    }

    public void moveEnemies (Player s) {
        if (!enemies.isEmpty()) {
            for (Enemy e: enemies) {
                checkCollisions(s);
                checkOutOfBounds();
                e.moveEnemy();                
            }
        }
    }

    public int getProjectileDirection () {
        int a = 0;
        if (angle == 0) {
            a = 0;
        }
        if (angle == 90) {
            a = 3;
        }
        if (angle == 180) {
            a = 1;
        }
        if (angle == 270) {
            a = 2;
        }
        return a;
    }

    public void moveHearts (Player s) {
        if (!hearts.isEmpty()) {
            for (Heart h: hearts) {
                checkCollisions(s);
                checkOutOfBounds();
                h.moveHeart();                
            }
        }
    }

    public void setSpawn (double s) {
        spawnX = s;
    }

    public void setSeed (long s) {
        seed = s;
        random.setSeed(seed);
    }

    public void generateEnemy (int d) {
        double r = random.nextDouble();
        if (d == 1) {
            double spawnX = w * r;
            Enemy e = new Enemy(spawnX, 1, e_Images.get(index), 1);
            enemies.add(e);
        }
        if (d == 2) {
            double spawnY = (h-platformheight) * r;
            Enemy e = new Enemy(1, spawnY, e_Images.get(index), 2);
            enemies.add(e);
        }
        if (d == 3) {
            double spawnY = (h-platformheight) * r;
            Enemy e = new Enemy((w-1), spawnY, e_Images.get(index), 3);
            enemies.add(e);
        }
    }

    public void generateLives () {
        double spawnX = w * random.nextDouble();
        Heart e = new Heart(spawnX, heart);
        hearts.add(e);
    }

    public void checkCollisions (Player s) {
        for (Enemy e: enemies) {
            if (e.isVisible()) {
                if (collision.enemyCollision(getSprite(0), e)) {
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
                if (collision.outOfBounds_E(e, w, h, platformheight)) {
                    e.setVisible(false); 
                }
            }
        }
        for (Projectile p: projectiles) {
            if (p.isVisible()) {
                if (collision.outOfBounds_P(p, w, h, platformheight)) {
                    p.setVisible(false); 
                }
            }
        }
        for (Heart heart: hearts) {
            if (heart.isVisible()) {
                if (collision.outOfBounds_H(heart, w, h, platformheight)) {
                    heart.setVisible(false); 
                }
            }
        }
        Player currentSprite = getSprite(0);
        if (collision.leftOutOfBounds(currentSprite)) {
            currentSprite.setX(w-currentSprite.getSize()-10);
        }
        if (collision.rightOutOfBounds(currentSprite, w)) {
            currentSprite.setX(10);
        }
        if (collision.upOutOfBounds(currentSprite)) {
            currentSprite.setY((h-platformheight)-(currentSprite.getSize()));
        }
        if (collision.downOutOfBounds(currentSprite, h)) {
            currentSprite.setY(10);
        }
    }

    public void Countdown () {
        time--;
    }

    public int getTime () {
        return time;
    }

    public int getLives () {
        return lives;
    }

    public int getScore () {
        return score;
    }

    public void setOppScore (int s) {
        oppScore = s;
    }

    public void setTheme (String s) {
        if (s.equalsIgnoreCase("Tank")) {
            index = 0;
        }
        else if (s.equalsIgnoreCase("Wizard")) {
            index = 1;
        }
        else if (s.equalsIgnoreCase("Space")) {
            index = 2;
        }
        else if (s.equalsIgnoreCase("Beach")) {
            index = 3;
        }
        else {
            System.out.println("Invalid theme");
        }
    }

    public void setAngle (int a) {
        angle = a;
    }

    public int getAngle () {
        return angle;
    }
}