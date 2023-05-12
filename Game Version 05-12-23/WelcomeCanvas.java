import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;
import java.io.*;
import java.net.*;

public class WelcomeCanvas extends JComponent {
    private int w, h;
    private GradientPaint gradient;
    private Sound sounds;
    private File sound;

    public WelcomeCanvas (int width, int height) {
        w = width;
        h = height;
        setPreferredSize(new Dimension(w, h));
        //sound = new File ("Song4.wav");
        //sounds = new Sound ();
    }

    @Override
    public void paintComponent (Graphics g) {
        Graphics2D g2d  = (Graphics2D) g;

        RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHints(rh);

        gradient = new GradientPaint((w/2), 0, Color.decode("#17202A"), (w/2), h, Color.decode("#2E4053"), false);
        g2d.setPaint(gradient);
        g2d.fillRect(0, 0, w, h);
        g2d.setFont(new Font("Dialog", Font.PLAIN, 50));
        g2d.setColor(Color.decode("#FFFFFF"));
        g2d.drawString (("WELCOME TO"), w*1/2-(g2d.getFontMetrics().stringWidth("WELCOME TO"))/2, (h-2*(h/3)));
        g2d.setFont(new Font("Courier", Font.BOLD, 80));
        g2d.drawString (("SHOOTER"), w*1/2-(g2d.getFontMetrics().stringWidth("SHOOTER"))/2, (h/2+20));
        g2d.setFont(new Font("Dialog", Font.PLAIN, 25));
        g2d.drawString (("PICK A THEME"), w*1/2-(g2d.getFontMetrics().stringWidth("PICK A THEME"))/2, (h-h/3));

        //playSound ();
    }

    public void playSound () {
        sounds.play (sound);
    }
}