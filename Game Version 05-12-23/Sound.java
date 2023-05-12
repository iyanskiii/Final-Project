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