import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage; 
import java.io.*;

public class GameOver extends JComponent {
    private int w, h, score;

    public GameOver (int w, int h, int score) {
        this.w = w;
        this.h = h;
        this.score = score;
    }

    @Override
    protected void paintComponent (Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHints(rh);

        Rectangle r = new Rectangle (0, 0, this.w, this.h, "#2E4053");
        r.draw(g2d);

        g2d.setFont(new Font("Copperplate", Font.BOLD, 120));
        g2d.setColor(Color.WHITE);
        g2d.drawString (("GAME OVER"), this.w*1/2-(g2d.getFontMetrics().stringWidth("GAME OVER"))/2, this.h/2);
        g2d.setFont(new Font("Dialog", Font.PLAIN, 40));
        g2d.setColor(Color.WHITE);
        g2d.drawString (("YOUR SCORE: " + this.score), this.w*1/2-(g2d.getFontMetrics().stringWidth("YOUR SCORE:   "))/2, this.h-(this.h/4));
    }
    
}