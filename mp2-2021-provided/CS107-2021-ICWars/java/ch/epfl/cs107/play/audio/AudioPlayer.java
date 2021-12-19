package ch.epfl.cs107.play.audio;

import ch.epfl.cs107.play.game.areagame.io.ResourcePath;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.*;

//handler for audio
public class AudioPlayer {
    private AudioInputStream sound;
    private Clip clip;
    private boolean loop = false;

    //load
    public AudioPlayer(String soundName) {
        //load the audio as input stream
        try {
            sound = AudioSystem.getAudioInputStream(new File(ResourcePath.getSound(soundName)).getAbsoluteFile());
            clip = AudioSystem.getClip();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }

    }



    public AudioPlayer(String soundName, boolean loop) {
        this(soundName);
        this.loop = loop;
    }

    /**
     * play loaded sound in the audio handler
     */
    public void playSound() {
        try {
            if (clip.isRunning()) {
                clip.stop();
                clip.close();
            }
            if (clip.isOpen()) {
                clip.close();
            }
            if (loop) {
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            }
            clip.open(sound);
            clip.setFramePosition(0);
            clip.start();


        } catch (LineUnavailableException | IOException e) {
            e.printStackTrace();
        }

    }
}