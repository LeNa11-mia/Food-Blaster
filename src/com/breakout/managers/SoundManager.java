package com.breakout.managers;

import com.breakout.config.GameConfig;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Handles loading and playback of in-game sound effects.
 *
 * <p>This class loads sound files when the game starts and provides
 * non-blocking playback using a fixed thread pool. Sound clips are reused
 * to avoid repeated loading overhead, improving performance.</p>
 */
public class SoundManager {

    /** Sound clip played when a brick is hit. */
    private static Clip brickHitClip;

    /** Sound clip played when the ball hits a wall. */
    private static Clip wallHitClip;

    /**
     * A fixed-size thread pool to handle sound playback without blocking
     * the main game loop.
     */
    private static final ExecutorService soundPool = Executors.newFixedThreadPool(2);

    /**
     * Loads all required sound effects.
     *
     * <p>This method should be called once during game initialization.</p>
     */
    public static void loadSounds() {
        try {
            brickHitClip = loadClip(GameConfig.BRICK_HIT_SOUND_PATH);
            wallHitClip = loadClip(GameConfig.WALL_HIT_SOUND_PATH);
        } catch (Exception e) {
            System.err.println("Failed to load sounds: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Loads an audio file from a given file path and returns it as a {@link Clip}.
     *
     * @param path The file system path to the .wav sound file.
     * @return A Clip ready for playback, or {@code null} if loading fails.
     * @throws LineUnavailableException If the audio line cannot be opened.
     * @throws IOException If the audio file cannot be read.
     * @throws UnsupportedAudioFileException If the file format is not supported.
     */
    public static Clip loadClip(String path) throws LineUnavailableException, IOException, UnsupportedAudioFileException {
        File audioFile = new File(path);

        if (!audioFile.exists()) {
            System.err.println("Sound file not found: " + path);
            return null;
        }

        AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
        Clip clip = AudioSystem.getClip();
        clip.open(audioStream);
        return clip;
    }

    /**
     * Plays the brick hit sound effect.
     *
     * <p>Playback is handled asynchronously to avoid blocking game performance.</p>
     */
    public static void playBrickHitSound() {
        if (brickHitClip != null) {
            soundPool.submit(() -> {
                synchronized (brickHitClip) {
                    brickHitClip.setFramePosition(0);
                    if (brickHitClip.isRunning()) {
                        brickHitClip.stop();
                    }
                    brickHitClip.start();
                }
            });
        }
    }

    /**
     * Plays the wall hit sound effect.
     *
     * <p>Playback is handled asynchronously to avoid blocking game performance.</p>
     */
    public static void playWallHitSound() {
        if (wallHitClip != null) {
            soundPool.submit(() -> {
                synchronized (wallHitClip) {
                    wallHitClip.setFramePosition(0);
                    if (wallHitClip.isRunning()) {
                        wallHitClip.stop();
                    }
                    wallHitClip.start();
                }
            });
        }
    }

    // Additional sound methods (e.g., menu click, game over) can be added here.
}
