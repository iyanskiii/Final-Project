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