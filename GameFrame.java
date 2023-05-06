import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;

public class GameFrame {
    private int w, h;
    private JFrame f;
    private JPanel p, buttons;
    private JButton tank, wizard, space, bball;
    private Canvas c;
    private GameOver go;
    private WelcomeCanvas wc;
    private Sprite currentSprite;
    private Timer Animator, EnemyGenerator, Countdown, LifeGenerator, LifeAnimator, LifeChecker, Renderer, moveTimer;
    private ArrayList<Timer> timers;
    private boolean running, left, right, up, down;

    public GameFrame () {
        w = 1000;
        h = 700;
        timers = new ArrayList<Timer>();
        running = true;
        tank = new JButton ("Tank");
        wizard = new JButton ("Wizard");
        space = new JButton ("Space");
        bball = new JButton ("Basketball");
        left = false;
        right = false;
        up = false;
        down = false;

    }

    public void setUp () {
        f = new JFrame();
        p = (JPanel) f.getContentPane();
        p.setFocusable (true);
        c = new Canvas (w, h);
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
        buttons.add(bball);
        bball.setBackground(Color.decode("#BF9867"));
        bball.setForeground(Color.decode("#000000"));
        bball.setOpaque(true);
        bball.setBorderPainted(false);
        f.add(buttons, BorderLayout.SOUTH);
        f.pack();
        f.setVisible(true);
        f.setResizable(false);
    }

    public void setUpTimersAndKeyBindings () {

        tank.addActionListener(new ActionListener () {
            @Override
            public void actionPerformed (ActionEvent e) {
                c.setTheme("Tank");
                startGame();
            }
        });

        wizard.addActionListener(new ActionListener () {
            @Override
            public void actionPerformed (ActionEvent e) {
                c.setTheme("Wizard");
                startGame();
            }
        });

        space.addActionListener(new ActionListener () {
            @Override
            public void actionPerformed (ActionEvent e) {
                c.setTheme("Space");
                startGame();
            }
        });

        bball.addActionListener(new ActionListener () {
            @Override
            public void actionPerformed (ActionEvent e) {
                c.setTheme("Basketball");
                startGame();
            }
        });

        ActionMap am = p.getActionMap();
        InputMap im = p.getInputMap();
        currentSprite = c.getSprite();

        Renderer = new Timer (1, new ActionListener () {
            public void actionPerformed (ActionEvent ae) {
                c.repaint();
            }
        });
        timers.add(Renderer);

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

        EnemyGenerator = new Timer (1000, new ActionListener () {
            public void actionPerformed (ActionEvent ae) {
                c.generateEnemy();
            }
        });
        timers.add(EnemyGenerator);

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
                    gameOverScreen();
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
                    gameOverScreen();
                    f.repaint();
                    Countdown.stop(); 
                }
            }
        });
        timers.add(Countdown);

        AbstractAction right_true = new AbstractAction () {
            public void actionPerformed(ActionEvent ae) {
                right = true;
            }
        };

        AbstractAction right_false = new AbstractAction () {
            public void actionPerformed(ActionEvent ae) {
                right = false;
            }
        };

        AbstractAction left_true = new AbstractAction () {
            public void actionPerformed(ActionEvent ae) {
                left = true;
            }
        };

        AbstractAction left_false = new AbstractAction () {
            public void actionPerformed(ActionEvent ae) {
                left = false;
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
            }
        };

        AbstractAction down_false = new AbstractAction () {
            public void actionPerformed(ActionEvent ae) {
                down = false;
            }
        };

        AbstractAction s = new AbstractAction () {
            public void actionPerformed(ActionEvent ae) {
                c.addProjectile(currentSprite);
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

    public void gameOverScreen () {
        go = new GameOver (w, h, c.getScore());
        stopTimers();
        f.remove(c);
        f.add(go, BorderLayout.CENTER);
        f.revalidate();
        f.repaint();
    }

    public void startGame () {
        f.remove(wc);
        f.remove(buttons);
        f.add(c, BorderLayout.CENTER);
        startTimers();
        f.revalidate();
        f.repaint();
    }
    
}