import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage; 
import java.io.*;

public class Canvas extends JComponent {
    public int w, h;
    private int platformheight, score, lives, scoreLength, time, index, angle;
    private long seed;
    private Random random;
    private double spawnX;
    private String scoreText, theme;
    private ArrayList<Sprite> sprites;
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

    public Canvas (int width, int height, long s) {
        w = width;
        h = height;
        s = seed;
        random = new Random (seed);
        angle = 0;
        platformheight = 80;
        score = 0;
        lives = 3;
        time = 120;
        setPreferredSize(new Dimension(w, h));
        sprites = new ArrayList<Sprite>();
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
            s_Images.add(ImageIO.read(getClass().getResourceAsStream("assets/gsw.png")));

            e_Images.add(ImageIO.read(getClass().getResourceAsStream("assets/enemy.png")));
            e_Images.add(ImageIO.read(getClass().getResourceAsStream("assets/ghost.png")));
            e_Images.add(ImageIO.read(getClass().getResourceAsStream("assets/tiefighter.png")));
            e_Images.add(ImageIO.read(getClass().getResourceAsStream("assets/lakers.png")));
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

        sprites.add(new Sprite(s_Images.get(0), 10,(h-platformheight)-80, 80));

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
        g2d.setFont(new Font(Font.SERIF, Font.BOLD, 40));
        scoreText = ("Score: " + score);
        scoreLength = (g2d.getFontMetrics().stringWidth("Score:   "));
        g2d.drawString (scoreText, w/2-scoreLength/2, h-platformheight/2);
        g2d.drawString (("Lives: " + lives), w*3/4-(g2d.getFontMetrics().stringWidth("Lives:  "))/2, h-platformheight/2);
        g2d.drawString (("Time: " + time), w*1/4-(g2d.getFontMetrics().stringWidth("Time:   "))/2, h-platformheight/2);
    }

    public Sprite getSprite (int i) {
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

    public void addProjectile (Sprite s, int d) {
        Projectile p = new Projectile(s, d);
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

    public void moveHearts (Sprite s) {
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
    }

    public void generateEnemy () {
        double spawnX = w * random.nextDouble(); //seed = sequence of random number
        //System.out.println(spawnX);
        Enemy e = new Enemy(spawnX, e_Images.get(index));
        enemies.add(e);
    }

    public void generateLives () {
        double spawnX = w * random.nextDouble();
        Heart e = new Heart(spawnX, heart);
        hearts.add(e);
    }

    public void checkCollisions (Sprite s) {
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
        Sprite currentSprite = getSprite(0);
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
        else if (s.equalsIgnoreCase("Basketball")) {
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