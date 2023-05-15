/*
Brief Description of this Class:
MusicHandler uses the Sound class in order to manage the playing of the soundtrack and playing of sound effects. 
This class also adds .wav files that are played in the Sound class.
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


import java.io.*;
import java.util.*;

public class MusicHandler {
    private ArrayList <File> audios, soundEffects;
    private File projectileSound;
    private Sound s;

    public MusicHandler () {
        s = new Sound ();
        audios = new ArrayList <File> ();
        soundEffects = new ArrayList <File> ();

        audios.add(new File ("menu.wav"));
        audios.add(new File ("song1.wav"));
        audios.add(new File ("song2.wav"));
        audios.add(new File ("song3.wav"));
        audios.add(new File ("song4.wav"));
        audios.add(new File ("song5.wav"));
        audios.add(new File ("song6.wav"));
        audios.add(new File ("gameover.wav"));

        soundEffects.add(new File ("blaster.wav"));
        s.setAudio (soundEffects.get(0));
    }

    public void menuMusic () {
        s.play (audios.get(0));
    }

    public void gameMusic () {
        s.stop ();
        int i = 1 + (int)((audios.size()-2) * Math.random());
        s.play (audios.get(i));
    }

    public void gameOverMusic () {
        s.stop ();
        s.play (audios.get(audios.size()-1));
    }

    public void projectileSound () {
        s.playOnce ();
        s.c2_setDelay (150000);
    }
}