package com.breakout.managers;

import com.breakout.config.GameConfig;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * {@code SoundManager} is responsible for loading, managing, and playing
 * sound clips using a fixed thread pool to ensure non-blocking playback.
 */
public class SoundManager {
    /** Clip for the sound played when the ball hits a brick. */
    private static Clip brickHitClip;
    /** Clip for the sound played when the ball hits a wall or the paddle. */
    private static Clip wallHitClip;

    /** Thread pool with a fixed number of threads for non-blocking sound playback. */
    private static final ExecutorService soundPool = Executors.newFixedThreadPool(2);

    /**
     * Loads all necessary sound clips when the game starts.
     * Catches and reports any errors during the loading process.
     */
    public static void loadSounds() {
        try {
            // Load brick hit sound
            brickHitClip = loadClip(GameConfig.BRICK_HIT_SOUND_PATH);
            // Load wall/paddle hit sound
            wallHitClip = loadClip(GameConfig.WALL_HIT_SOUND_PATH);
        } catch (final Exception e) {
            System.err.println("Error loading sounds: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Loads a {@link Clip} from the specified file path.
     *
     * @param path The file system path to the audio file.
     * @return The loaded {@link Clip} object, or {@code null} if the file is not found.
     * @throws LineUnavailableException If a line cannot be opened because it is unavailable.
     * @throws IOException If an I/O exception occurs during file reading.
     * @throws UnsupportedAudioFileException If the file format is not supported.
     */
    public static Clip loadClip(final String path) throws LineUnavailableException, IOException, UnsupportedAudioFileException {
        // Must ensure the File path is correct in your environment
        final File audioFile = new File(path);

        if (!audioFile.exists()) {
            System.err.println("Audio file not found: " + path);
            return null;
        }

        final AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
        final Clip clip = AudioSystem.getClip();
        clip.open(audioStream);
        return clip;
    }

    /**
     * Plays the brick hit sound.
     * The playback is handled in a separate thread to avoid blocking the game loop.
     */
    public static void playBrickHitSound() {
        if (brickHitClip != null) {
            // Submit the playback task to the thread pool
            soundPool.submit(() -> {
                // Synchronize access to the clip object to prevent data race issues
                synchronized (brickHitClip) {
                    // Reset clip to the beginning
                    brickHitClip.setFramePosition(0);
                    // Stop if currently playing (allows for rapid replay)
                    if (brickHitClip.isRunning()) {
                        brickHitClip.stop();
                    }
                    // Play the sound
                    brickHitClip.start();
                }
            });
        }
    }

    /**
     * Plays the wall/paddle hit sound.
     * The playback is handled in a separate thread to avoid blocking the game loop.
     */
    public static void playWallHitSound() {
        if (wallHitClip != null) {
            soundPool.submit(() -> {
                synchronized (wallHitClip) {
                    // Reset clip to the beginning
                    wallHitClip.setFramePosition(0);
                    // Stop if currently playing (allows for rapid replay)
                    if (wallHitClip.isRunning()) {
                        wallHitClip.stop();
                    }
                    // Play the sound
                    wallHitClip.start();
                }
            });
        }
    }

    // Additional methods for other sounds (Menu Click, Game Over, etc.) can be added here.
}