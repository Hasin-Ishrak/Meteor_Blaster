package objects.Sound;

import java.io.IOException;
import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
//import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class sound {
      private final URL shoot;
    private final URL space;

    public sound() {
        this.shoot = this.getClass().getClassLoader().getResource("objects/Sound/shoot 2.wav");
        this.space = this.getClass().getClassLoader().getResource("objects/Sound/space.wav");
        
    }
    public void soundShoot() {
        play(shoot,false);
    }

    public void soundSpace() {
        play(space,true);
    }

    private void play(URL url, boolean loop) {
        try {
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);

            if (loop) {
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            } else {
                clip.addLineListener(event -> {
                    if (event.getType() == LineEvent.Type.STOP) {
                        clip.close();
                    }
                });
            }

            clip.start();

        } catch (IOException | LineUnavailableException | UnsupportedAudioFileException e) {
            System.err.println("Error playing sound: " + e.getMessage());
        }
    }
}