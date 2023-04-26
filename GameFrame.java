import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.net.*;
//I import the code that allows to connect to the server. 

public class GameFrame {
    private int w, h;
    private JFrame f;
    private JPanel p;
    private Canvas c;
    private Sprite currentSprite;
    private Timer Animator, EnemyGenerator, Countdown, LifeGenerator, LifeAnimator;
    private boolean running;
    private Socket socket;
    private int playerId; // gets a value after you connect to the server 

    public GameFrame () {
        w = 1000;
        h = 700;
    }

    public void setUp () {
        f = new JFrame();
        p = (JPanel) f.getContentPane();
        p.setFocusable (true);
        c = new Canvas (w, h);
        f.setTitle("Game Practice");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add (c, BorderLayout.CENTER);
        f.pack();
        f.setVisible(true);
        running = true;
    }

    public void addKeyBindings () {
        ActionMap am = p.getActionMap();
        InputMap im = p.getInputMap();
        currentSprite = c.getSprite();

        AbstractAction r = new AbstractAction () {
            public void actionPerformed(ActionEvent ae) {
                currentSprite.moveSpriteX(20);
                c.repaint();
                //System.out.println("right");
            }
        };

        AbstractAction l = new AbstractAction () {
            public void actionPerformed(ActionEvent ae) {
                currentSprite.moveSpriteX(-20);
                c.repaint();
                //System.out.println("left");
            }
        };

        EnemyGenerator = new Timer (2000, new ActionListener () {
            public void actionPerformed (ActionEvent ae) {
                c.generateEnemy();
            }
        });

        LifeGenerator = new Timer (10000, new ActionListener () {
            public void actionPerformed (ActionEvent ae) {
                c.generateLives();
            }
        });

        EnemyGenerator.start();
        //LifeGenerator.start();

        Animator = new Timer (5, new ActionListener () {
            public void actionPerformed (ActionEvent ae) {
                c.shootProjectile(currentSprite);
                c.moveEnemies(currentSprite);
                c.repaint();
            }
        });

        LifeAnimator = new Timer (5, new ActionListener () {
            public void actionPerformed (ActionEvent ae) {
                c.moveHearts(currentSprite);
                c.repaint();
            }
        });

        Animator.start();
        LifeAnimator.start();

        Countdown = new Timer (1000, new ActionListener () {
            public void actionPerformed (ActionEvent ae) {
                c.Countdown();
            }
        });
        Countdown.start();
        if (c.getTime() == 0) {
            Countdown.stop();
        }

        AbstractAction j = new AbstractAction () {
            public void actionPerformed(ActionEvent ae) {
                c.addProjectile(currentSprite);
            }
        };

        
        am.put ("right", r);
        im.put (KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0, false), "right");
        am.put ("left", l);
        im.put (KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0, false), "left");
        am.put ("jump", j);
        im.put (KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0, false), "jump");
    }
    private void connectToServer{
        try{
            socket = new Socket("localhost", 49501)
            DataInputStream in = new DataInputStream(socket.getInputStream);
            DataOutputStream out = new DataOutputStream(socket.getOutputStream);
            playerId = in.readUTF();
            System.outprintln("You are player #" + playerId);
            if (playerId == 1){
                System.out.println("Waiting for player number 2 to connect");
            }
        }catch(IOException ex){
            System.outprintln("IOException from connect to server");
        }
    }

}
