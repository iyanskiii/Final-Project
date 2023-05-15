/*
Brief Description of this Class:
GameFrame is mainly responsible for the GUI of the game, as well as handling the timers and threads responsible for running the game.
These timers include timers for animating the player sprite, projectiles, enemies, etc. There are also timers for generating enemies and hearts. 
There is also a thread responsible fro rendering or repainting the screen. 
GameFrame is also responsible for connecting to the GameServer, as well as writing and recieving data to share scores with the other player. 
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
import java.util.ArrayList;
import java.io.*;
import java.net.*;

public class GameFrame {
    private int w, h, playerID, currentScore, port;
    private String ip;
    private long seed;
    private JFrame f;
    private JPanel p, buttons;
    private JButton tank, wizard, space, beach;
    private GameCanvas c;
    private GameOver go;
    private WelcomeCanvas wc;
    private Player currentSprite;
    private Timer Animator, EnemyGeneratorDown, EnemyGeneratorLeft, EnemyGeneratorRight, Countdown, LifeGenerator, LifeAnimator, LifeChecker, Renderer, moveTimer;
    private ArrayList<Timer> timers;
    private boolean running, bothRunning, left, right, up, down;
    private MusicHandler music;
    private Socket socket;
    private ReadFromServer rfs;
    private WriteToServer wts;
    private Thread t;

    public GameFrame (String s, int i) {
        ip = s;
        port = i;
        w = 1000;
        h = 700;
        currentScore = 0;
        timers = new ArrayList<Timer>();
        running = true;
        tank = new JButton ("Tank");
        wizard = new JButton ("Wizard");
        space = new JButton ("Space");
        beach = new JButton ("Beach");
        left = false;
        right = false;
        up = false;
        down = false;
        running = false;
        music = new MusicHandler ();
        System.out.println("IP Address: " + ip + "; Port: " + port);

    }

    public void setUp () {
        f = new JFrame();
        p = (JPanel) f.getContentPane();
        p.setFocusable (true);
        c = new GameCanvas (w, h, 0);
        wc = new WelcomeCanvas (w, h);
        f.setTitle("Game Practice");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add (wc, BorderLayout.CENTER);
        buttons = new JPanel();
        buttons.setBackground(Color.decode("#2E4053"));
        buttons.setLayout(new GridLayout(1,3));
        buttons.add(tank);
        tank.setBackground(Color.decode("#4F5326"));
        tank.setForeground(Color.decode("#FFFFFF"));
        tank.setOpaque(true);
        tank.setBorderPainted(false);
        buttons.add(wizard);
        wizard.setBackground(Color.decode("#6B4188"));
        wizard.setForeground(Color.decode("#FFFFFF"));
        wizard.setOpaque(true);
        wizard.setBorderPainted(false);
        buttons.add(space);
        space.setBackground(Color.decode("#212F3C"));
        space.setForeground(Color.decode("#FFFFFF"));
        space.setOpaque(true);
        space.setBorderPainted(false);
        buttons.add(beach);
        beach.setBackground(Color.decode("#BF9867"));
        beach.setForeground(Color.decode("#000000"));
        beach.setOpaque(true);
        beach.setBorderPainted(false);
        f.add(buttons, BorderLayout.SOUTH);
        f.pack();

        Runnable r = new Renderer ();
        t = new Thread (r);
        t.start();

        f.setVisible(true);
        f.setResizable(false);
        music.menuMusic ();
    }

    private class Renderer implements Runnable {
        public Renderer () {}
        @Override
        public void run () {
            while (true) {
                try {
                    c.repaint ();
                    Thread.sleep(5);
                }
                catch(Exception e) {}
            }
        }
    }

    public void setUpTimersAndKeyBindings () {

        tank.addActionListener(new ActionListener () {
            @Override
            public void actionPerformed (ActionEvent e) {
                c.setTheme("Tank");
                connectToServer();
            }
        });

        wizard.addActionListener(new ActionListener () {
            @Override
            public void actionPerformed (ActionEvent e) {
                c.setTheme("Wizard");
                connectToServer();
            }
        });

        space.addActionListener(new ActionListener () {
            @Override
            public void actionPerformed (ActionEvent e) {
                c.setTheme("Space");
                connectToServer();
            }
        });

        beach.addActionListener(new ActionListener () {
            @Override
            public void actionPerformed (ActionEvent e) {
                c.setTheme("Beach");
                connectToServer();
            }
        });

        ActionMap am = p.getActionMap();
        InputMap im = p.getInputMap();
        currentSprite = c.getSprite(0);

        moveTimer = new Timer (2, new ActionListener () {
            public void actionPerformed (ActionEvent ae) {
                if (right) {
                    currentSprite.moveX(1);
                }
                if (left) {
                    currentSprite.moveX(-1);
                }
                if (up) {
                    currentSprite.moveY(-1);
                }
                if (down) {
                    currentSprite.moveY(1);
                }
            }
        });
        timers.add(moveTimer);

        EnemyGeneratorDown = new Timer (2000, new ActionListener () {
            public void actionPerformed (ActionEvent ae) {
                c.generateEnemy(1);
            }
        });
        timers.add(EnemyGeneratorDown);

        EnemyGeneratorLeft = new Timer (3500, new ActionListener () {
            public void actionPerformed (ActionEvent ae) {
                c.generateEnemy(2);
            }
        });
        timers.add(EnemyGeneratorLeft);

        EnemyGeneratorRight = new Timer (5000, new ActionListener () {
            public void actionPerformed (ActionEvent ae) {
                c.generateEnemy(3);
            }
        });
        timers.add(EnemyGeneratorRight);

        LifeGenerator = new Timer (15000, new ActionListener () {
            public void actionPerformed (ActionEvent ae) {
                c.generateLives();
            }
        });
        timers.add(LifeGenerator);

        Animator = new Timer (3, new ActionListener () {
            public void actionPerformed (ActionEvent ae) {
                c.shootProjectile(currentSprite);
                c.moveEnemies(currentSprite);
            }
        });
        timers.add(Animator);

        LifeAnimator = new Timer (3, new ActionListener () {
            public void actionPerformed (ActionEvent ae) {
                c.moveHearts(currentSprite);
            }
        });
        timers.add(LifeAnimator);

        LifeChecker = new Timer (800, new ActionListener () {
            public void actionPerformed (ActionEvent ae) {
                if (c.getLives() == 0) {
                    gameOverScreen(false);
                }
            }
        });
        timers.add(LifeChecker);

        Countdown = new Timer (1000, new ActionListener () {
            public void actionPerformed (ActionEvent ae) {
                if (c.getTime() > 0) {
                    c.Countdown();
                }
                else {
                    gameOverScreen(true);
                    f.repaint();
                    Countdown.stop(); 
                }
            }
        });
        timers.add(Countdown);

        AbstractAction right_true = new AbstractAction () {
            public void actionPerformed(ActionEvent ae) {
                right = true;
                c.setAngle(90);
            }
        };

        AbstractAction right_false = new AbstractAction () {
            public void actionPerformed(ActionEvent ae) {
                right = false;
                c.setAngle(0);
            }
        };

        AbstractAction left_true = new AbstractAction () {
            public void actionPerformed(ActionEvent ae) {
                left = true;
                c.setAngle(270);
            }
        };

        AbstractAction left_false = new AbstractAction () {
            public void actionPerformed(ActionEvent ae) {
                left = false;
                c.setAngle(0);
            }
        };

        AbstractAction up_true = new AbstractAction () {
            public void actionPerformed(ActionEvent ae) {
                up = true;
            }
        };

        AbstractAction up_false = new AbstractAction () {
            public void actionPerformed(ActionEvent ae) {
                up = false;
            }
        };

        AbstractAction down_true = new AbstractAction () {
            public void actionPerformed(ActionEvent ae) {
                down = true;
                c.setAngle(180);
            }
        };

        AbstractAction down_false = new AbstractAction () {
            public void actionPerformed(ActionEvent ae) {
                down = false;
                c.setAngle(0);
            }
        };

        AbstractAction s = new AbstractAction () {
            public void actionPerformed(ActionEvent ae) {
                if (running) {
                    c.addProjectile(currentSprite, c.getProjectileDirection());
                    music.projectileSound();
                }
            }
        };

        AbstractAction p1s = new AbstractAction () {
            public void actionPerformed(ActionEvent ae) {
                if (playerID == 1) {
                    c.addProjectile(currentSprite, c.getProjectileDirection());
                    music.projectileSound();
                }
            }
        };

        AbstractAction p2s = new AbstractAction () {
            public void actionPerformed(ActionEvent ae) {
                if (playerID == 2) {
                    c.addProjectile(currentSprite, c.getProjectileDirection());
                    music.projectileSound();
                }
            }
        };



        am.put ("right true", right_true);
        im.put (KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0, false), "right true");
        am.put ("right false", right_false);
        im.put (KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0, true), "right false");
        am.put ("left true", left_true);
        im.put (KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0, false), "left true");
        am.put ("left false", left_false);
        im.put (KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0, true), "left false");
        am.put ("up true", up_true);
        im.put (KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0, false), "up true");
        am.put ("up false", up_false);
        im.put (KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0, true), "up false");
        am.put ("down true", down_true);
        im.put (KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0, false), "down true");
        am.put ("down false", down_false);
        im.put (KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0, true), "down false");
        am.put ("shoot", s);
        im.put (KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0, false), "shoot");
        am.put ("shoot1", p1s);
        im.put (KeyStroke.getKeyStroke(KeyEvent.VK_Z, 0, false), "shoot1");
        am.put ("shoot2", p2s);
        im.put (KeyStroke.getKeyStroke(KeyEvent.VK_X, 0, false), "shoot2");

    }

    public void startTimers () {
        if (!timers.isEmpty()) {
            for (Timer t: timers) {
                t.start();
            }
        }
    }

    public void stopTimers () {
        if (!timers.isEmpty()) {
            for (Timer t: timers) {
                t.stop();
            }
        }
    }

    public void gameOverScreen (boolean survived) {
        if (survived) {
            go = new GameOver (w, h, c.getScore(), true);
        }
        else {
            go = new GameOver (w, h, c.getScore(), false);
        }
        
        stopTimers();
        t.interrupt();
        running = false;
        music.gameOverMusic();
        f.remove(c);
        f.add(go, BorderLayout.CENTER);
        f.revalidate();
        f.repaint();
    }

    public void startGame2 () {
        f.remove(wc);
        f.remove(buttons);
        f.add(c, BorderLayout.CENTER);
        startTimers();
        music.gameMusic();
        running = true;
        f.revalidate();
        f.repaint();
    }

    public void startGame () {
        running = true;
    }

    public void connectToServer() {
        try{
            socket = new Socket(ip, port);
            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            playerID = in.readInt(); 
            seed = in.readLong();
            System.out.println("Seed: " + seed);
            c.setSeed (seed);
            System.out.println("You are player #" + playerID);
            f.setTitle("Player " + playerID);
            if (playerID == 1){
                System.out.println("Waiting for Player #2 to connect...");
            }
            rfs = new ReadFromServer(in);
            wts = new WriteToServer(out);

            rfs.waitForStartMsg();
            startGame2();
        }
        catch(IOException ex){
            ex.printStackTrace();
        }
    }
    private class WriteToServer implements Runnable {

        private DataOutputStream dataOut;

        public WriteToServer(DataOutputStream out){
            dataOut = out;
            System.out.println("WTS Runnable created"); 
        }    
        @Override
        public void run() {
            while (true) {
                try {
                    dataOut.writeInt (c.getScore());
                    dataOut.flush(); 
                    Thread.sleep (1000);
                }
                catch(Exception ex) {
                    System.out.println("Exception from WTS run()");
                }
            }
        }
    }

    private class ReadFromServer implements Runnable {

        private DataInputStream dataIn;

        public ReadFromServer(DataInputStream in){
            dataIn = in;
            System.out.println("RFS Runnable created");
        }
        public void run() {
            int p1Score;
            int p2Score;
            while (true) {
                try {
                    if (playerID == 1) {
                        p2Score = dataIn.readInt();                    
                        System.out.println("p2 Score: " + p2Score);
                        c.setOppScore(p2Score);
                    }
                    else {
                        p1Score = dataIn.readInt();
                        System.out.println("p1 Score: " + p1Score);
                        c.setOppScore(p1Score);
                    }
                }
                catch (Exception e) {
                    System.out.println("Exception from RFS run()");
                }
                
            }
        }
        public void waitForStartMsg () {
            try {
                System.out.println("Waiting for both players to start...");
                String startMsg = dataIn.readUTF();
                System.out.println(startMsg); 
                Thread readThread = new Thread (rfs);
                Thread writeThread = new Thread (wts);
                readThread.start();
                writeThread.start();
            }
            catch(IOException ex) {
                System.out.println("IOException from waitForStartMsg()");
            }
        }
    }

    /*private void connectToServer () {
        try {
            socket = new Socket ("localhost", 45371);
            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getInputStream());
            playerID = in.readInt();
            System.out.println ("Welcome Player " + playerID);
            if (playerID == 1) {
                System.out.println("Waiting for Player 2 to connect.");
            }
            rfs = new ReadFromServer(in);
            wts = new WriteToServer(out);
        }
        catch (Exception e) {
            System.out.println("Exception from connectToServer.");
        }
    }

    private class ReadFromServer implements Runnable {
        private DataInputStream dataIn;

        public ReadFromServer (DataInputStream in) {
            dataIn = in;
            System.out.println("RFS Runnable Created");
        }

        public void run ()  {
            try{
                while (running) {
                    c.setOtherX(dataIn.readDouble());
                    c.setOtherY(dataIn.readDouble());
                }
            }
            catch (Exception e){
                System.out.println("Exception from RFS run");
            }
        }
    }

    private class WriteToServer implements Runnable {
        private DataOutputStream dataOut;

        public WriteToServer (DataOutputStream in) {
            dataOut = out;
            System.out.println("RFS Runnable Created");
        }

        public void run ()  {
            try {
                while (running) {
                    dataOut.writeDouble(currentSprite.getX());
                    dataOut.writeDouble(currentSprite.getY());
                    dataOut.flush();
                    try {
                        Thread.sleep(25);
                    }
                    catch (InterruptedException e) {
                        System.out.println("Interrupted Exception");
                    }

                }
            }
            catch (Exception e) {
                System.out.println("Exception from WTS run")
            }
        }
    }*/

}