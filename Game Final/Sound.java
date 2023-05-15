/*
Brief Description of this Class:
This class imports javax.sound.sampled.* in order to play a .wav file. It contains methods to loop a track or play it once. 
The playing of sounds is handled by the MusicHandler class.
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
import javax.sound.sampled.*;

public class Sound {
    private Clip c, c2;
    private AudioInputStream stream1, stream2;

    public Sound () {

        try {
            c = AudioSystem.getClip();
            c2 = AudioSystem.getClip();
        }
        catch (Exception e) {System.out.println("error");}
    
    }

    public void play (File  file) {
        try {
            stream1 = AudioSystem.getAudioInputStream(file);
            c.open (stream1);
        }
        catch (Exception e) {System.out.println("error");}
        c.start();
        c.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop () {
        c.close ();
        c.stop ();
    }

    public void setAudio (File file) {
        try {
            stream2 = AudioSystem.getAudioInputStream(file);
            c2.open(stream2);
        }
        catch (Exception e) {
           //System.out.println(e.getMessage()); 
        }
    }

    public void playOnce () {
        c2.start();

    }

    public void c2_setDelay (int i) {
        c2.setMicrosecondPosition (i);
    }
}