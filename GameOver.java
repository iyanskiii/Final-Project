/*
Brief Description of this Class:
GameOver is the canvas class responsible for painting the screen that is shown when the player loses all of their lives, or the timer runs out.
This displays whether the player survived or did not survive. This also displays the final score of the player.  
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

public class GameOver extends JComponent {
    private int w, h, score, oppScore;
    private boolean survived, otherSurvived;

    public GameOver (int w, int h, int s, boolean lived) {
        this.w = w;
        this.h = h;
        score = s;
        survived = lived;
    }

    @Override
    protected void paintComponent (Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHints(rh);

        Rectangle r = new Rectangle (0, 0, this.w, this.h, "#2E4053");
        r.draw(g2d);
        g2d.setColor(Color.WHITE);

        g2d.setFont(new Font("Copperplate", Font.BOLD, 80));
        if (survived) {
            g2d.drawString (("CONGRATULATIONS, YOU SURVIVED!"), this.w*1/2-(g2d.getFontMetrics().stringWidth("CONGRATULATIONS, YOU SURVIVED!"))/2, this.h/2);
            g2d.setFont(new Font("Dialog", Font.PLAIN, 40));
            g2d.setColor(Color.WHITE);
            g2d.drawString (("YOUR SCORE: " + this.score), this.w*1/2-(g2d.getFontMetrics().stringWidth("YOUR SCORE:   "))/2, this.h-(this.h/4));
        }
        else {
            g2d.drawString (("YOU DID NOT SURVIVE"), this.w*1/2-(g2d.getFontMetrics().stringWidth("YOU DID NOT SURVIVE"))/2, this.h/2);
            g2d.setFont(new Font("Dialog", Font.PLAIN, 40));
            g2d.setColor(Color.WHITE);
            g2d.drawString (("YOU'LL GET 'EM NEXT TIME"), this.w*1/2-(g2d.getFontMetrics().stringWidth("YOU'LL GET 'EM NEXT TIME"))/2, this.h-(this.h*2/5));
            g2d.setFont(new Font("Dialog", Font.PLAIN, 25));
            g2d.drawString (("YOUR SCORE: " + this.score), this.w*1/2-(g2d.getFontMetrics().stringWidth("YOUR SCORE:   "))/2, this.h-(this.h/5));
        }
        
    }
    
}
