package tankrotationexample.game;

import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class Sound {
    private Clip audioClip;

    // constructor to initialize a Sound object with a given Clip
    public Sound(Clip audioClip) {
        this.audioClip = audioClip;
    }

    // play the sound clip
    public void playAudio() {
        // show where I am in the clip
        this.audioClip.setFramePosition(0); // reset the playback position to the beginning
        this.audioClip.start();  // start playing the sound
    }

    // set the sound clip to loop indefinitely
    public void setLooping() {
        // loop sound indefinitely
        this.audioClip.loop(Clip.LOOP_CONTINUOUSLY); // set the loop count to loop indefinitely
    }

    // stop playing the sound clip
    public void stopAudio() {
        if (this.audioClip.isRunning()) {
            this.audioClip.stop(); // stop the sound playback
        }
    }

    // Set the volume level of the sound clip
    public void setVolume(float volumeLevel) {
        FloatControl volumeControl = (FloatControl) this.audioClip.getControl(FloatControl.Type.MASTER_GAIN);
        volumeControl.setValue(20.0f * (float) Math.log10(volumeLevel)); // convert volume level to dB scale
    }
}
