/*
Brief Description of this Class:
WelcomeCanvas is the canvas class responsible for painting the starting screen. This is displayed while waiting for the other player to start. 
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

public class WelcomeCanvas extends JComponent {
    private int w, h;
    private GradientPaint gradient;
    private Sound sounds;
    private File sound;

    public WelcomeCanvas (int width, int height) {
        w = width;
        h = height;
        setPreferredSize(new Dimension(w, h));/*
Brief Description of this Class:
WelcomeCanvas is the canvas class responsible for painting the starting screen. This is displayed while waiting for the other player to start. 
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

public class WelcomeCanvas extends JComponent {
    private int w, h;
    private GradientPaint gradient;
    private Sound sounds;
    private File sound;

    public WelcomeCanvas (int width, int height) {
        w = width;
        h = height;
        setPreferredSize(new Dimension(w, h));
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
        g2d.drawString (("NO MAN'S LAND"), w*1/2-(g2d.getFontMetrics().stringWidth("NO MAN'S LAND"))/2, (h/2+20));
        g2d.setFont(new Font("Dialog", Font.PLAIN, 20));
        g2d.drawString (("A GAME BY FRANCIS BAUTISTA & IAN ROQUE FEROL"), w*1/2-(g2d.getFontMetrics().stringWidth("A GAME BY FRANCIS BAUTISTA & IAN ROQUE FEROL"))/2, (h-h/3-20));
        g2d.setFont(new Font("Dialog", Font.PLAIN, 15));
        g2d.drawString (("SHOOT THE ENEMIES. TRY NOT TO DIE."), w*1/2-(g2d.getFontMetrics().stringWidth("SHOOT THE ENEMIES. TRY NOT TO DIE."))/2, (h-h/3+60));
        g2d.drawString (("PICK A THEME"), w*1/2-(g2d.getFontMetrics().stringWidth("PICK A THEME"))/2, (h-h/3+80));
    }

    public void playSound () {
        sounds.play (sound);
    }
}
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
        g2d.drawString (("NO MAN'S LAND"), w*1/2-(g2d.getFontMetrics().stringWidth("NO MAN'S LAND"))/2, (h/2+20));
        g2d.setFont(new Font("Dialog", Font.PLAIN, 20));
        g2d.drawString (("A GAME BY FRANCIS BAUTISTA & IAN ROQUE FEROL"), w*1/2-(g2d.getFontMetrics().stringWidth("A GAME BY FRANCIS BAUTISTA & IAN ROQUE FEROL"))/2, (h-h/3-20));
        g2d.setFont(new Font("Dialog", Font.PLAIN, 15));
        g2d.drawString (("SHOOT THE ENEMIES. TRY NOT TO DIE."), w*1/2-(g2d.getFontMetrics().stringWidth("SHOOT THE ENEMIES. TRY NOT TO DIE."))/2, (h-h/3+60));
        g2d.drawString (("PICK A THEME"), w*1/2-(g2d.getFontMetrics().stringWidth("PICK A THEME"))/2, (h-h/3+80));
    }

    public void playSound () {
        sounds.play (sound);
    }
}
