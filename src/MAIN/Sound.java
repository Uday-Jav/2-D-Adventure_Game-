package MAIN;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;

/**
 * Sound management system supporting background music and sound effects.
 */
public class Sound {

    private Clip clip;
    private final String[] soundFiles = new String[30];
    private FloatControl fc;
    private int volumeScale = 3; // 0 to 5
    private float volume;
    private boolean muted = false;

    // Sound IDs
    public static final int SFX_SLASH = 0;
    public static final int SFX_HIT_MONSTER = 1;
    public static final int SFX_HIT_PLAYER = 2;
    public static final int SFX_COIN = 3;
    public static final int SFX_CHEST = 4;
    public static final int SFX_DOOR = 5;
    public static final int SFX_POWERUP = 6;
    public static final int SFX_PUZZLE = 7;
    public static final int SFX_GAMEOVER = 8;
    public static final int SFX_VICTORY = 9;

    public static final int BGM_VILLAGE = 10;
    public static final int BGM_FOREST = 11;
    public static final int BGM_DUNGEON = 12;
    public static final int BGM_BOSS = 13;

    public Sound() {
        soundFiles[SFX_SLASH] = "/src/sound/sword_slash.wav";
        soundFiles[SFX_HIT_MONSTER] = "/src/sound/hit_monster.wav";
        soundFiles[SFX_HIT_PLAYER] = "/src/sound/hit_player.wav";
        soundFiles[SFX_COIN] = "/src/sound/coin_pickup.wav";
        soundFiles[SFX_CHEST] = "/src/sound/chest_open.wav";
        soundFiles[SFX_DOOR] = "/src/sound/door_unlock.wav";
        soundFiles[SFX_POWERUP] = "/src/sound/powerup.wav";
        soundFiles[SFX_PUZZLE] = "/src/sound/puzzle_solve.wav";
        soundFiles[SFX_GAMEOVER] = "/src/sound/game_over.wav";
        soundFiles[SFX_VICTORY] = "/src/sound/victory_fanfare.wav";

        soundFiles[BGM_VILLAGE] = "/src/sound/theme_village.wav";
        soundFiles[BGM_FOREST] = "/src/sound/theme_forest.wav";
        soundFiles[BGM_DUNGEON] = "/src/sound/theme_dungeon.wav";
        soundFiles[BGM_BOSS] = "/src/sound/theme_boss.wav";
    }

    public void setFile(int i) {
        if (i < 0 || i >= soundFiles.length || soundFiles[i] == null) return;
        try {
            InputStream is = getClass().getResourceAsStream(soundFiles[i]);
            if (is == null) {
                // fallback try without /src
                String alt = soundFiles[i].replace("/src", "");
                is = getClass().getResourceAsStream(alt);
            }
            if (is != null) {
                InputStream bufferedIn = new BufferedInputStream(is);
                AudioInputStream ais = AudioSystem.getAudioInputStream(bufferedIn);
                clip = AudioSystem.getClip();
                clip.open(ais);
                if (clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
                    fc = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                    checkVolume();
                }
            }
        } catch (Exception e) {
            // Audio errors shouldn't crash the game
            System.err.println("Could not load sound: " + soundFiles[i] + " (" + e.getMessage() + ")");
        }
    }

    public void play() {
        if (muted || clip == null) return;
        clip.setFramePosition(0);
        clip.start();
    }

    public void loop() {
        if (muted || clip == null) return;
        clip.setFramePosition(0);
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop() {
        if (clip != null) {
            clip.stop();
            clip.close();
        }
    }

    public void checkVolume() {
        if (fc == null) return;
        switch (volumeScale) {
            case 0: volume = -80f; break; // mute
            case 1: volume = -20f; break;
            case 2: volume = -12f; break;
            case 3: volume = -5f; break;
            case 4: volume = 1f; break;
            case 5: volume = 6f; break;
        }
        fc.setValue(volume);
    }

    public int getVolumeScale() {
        return volumeScale;
    }

    public void setVolumeScale(int scale) {
        this.volumeScale = Math.max(0, Math.min(5, scale));
        checkVolume();
    }

    public boolean isMuted() {
        return muted;
    }

    public void setMuted(boolean muted) {
        this.muted = muted;
        if (muted) {
            stop();
        }
    }
}
