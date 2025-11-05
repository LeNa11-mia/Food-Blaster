package com.breakout.managers;

import com.breakout.saves.GameSave;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date; // Explicitly importing Date for clarity

/**
 * {@code SaveManager} handles all operations related to persisting and retrieving
 * the game state to and from the file system using Java Serialization.
 */
public class SaveManager {
    /** Directory where save files are stored. */
    private static final String SAVE_DIR = "saves/";
    /** Full path and filename for the main save file. */
    private static final String SAVE_FILE = SAVE_DIR + "game_save.dat";

    static {
        // Create the 'saves/' directory if it does not exist when the class is loaded.
        final File dir = new File(SAVE_DIR);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    /**
     * Saves the current game state to a file using Java Serialization.
     *
     * @param gameSave The {@link GameSave} object containing the game state to be saved.
     * @return {@code true} if the save operation was successful, {@code false} otherwise.
     */
    public static boolean saveGame(final GameSave gameSave) {
        try {
            // Set the current save date
            gameSave.setSaveDate(new Date());

            final FileOutputStream fileOut = new FileOutputStream(SAVE_FILE);
            final ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);

            objectOut.writeObject(gameSave);

            objectOut.close();
            fileOut.close();

            System.out.println("Game saved successfully!");
            return true;

        } catch (final IOException e) {
            System.err.println("Error saving game: " + e.getMessage());
            return false;
        }
    }

    /**
     * Loads the saved game state from the file system using Java Deserialization.
     *
     * @return The loaded {@link GameSave} object, or {@code null} if the file does not exist or an error occurs.
     */
    public static GameSave loadGame() {
        try {
            final File file = new File(SAVE_FILE);
            if (!file.exists()) {
                return null;
            }

            final FileInputStream fileIn = new FileInputStream(SAVE_FILE);
            final ObjectInputStream objectIn = new ObjectInputStream(fileIn);

            final GameSave gameSave = (GameSave) objectIn.readObject();

            objectIn.close();
            fileIn.close();

            System.out.println("Game loaded successfully!");
            return gameSave;

        } catch (final IOException | ClassNotFoundException e) {
            System.err.println("Error loading game: " + e.getMessage());
            return null;
        }
    }

    /**
     * Checks if a save game file exists in the designated directory.
     *
     * @return {@code true} if the save file exists, {@code false} otherwise.
     */
    public static boolean saveExists() {
        final File file = new File(SAVE_FILE);
        return file.exists();
    }

    /**
     * Deletes the saved game file from the file system.
     */
    public static void deleteSave() {
        final File file = new File(SAVE_FILE);
        if (file.exists()) {
            final boolean deleted = file.delete();
            if (deleted) {
                System.out.println("Save game deleted!");
            } else {
                System.err.println("Failed to delete save game file!");
            }
        }
    }

    /**
     * Retrieves key information about the saved game for display purposes without
     * requiring a full game load.
     *
     * @return A formatted string with level, score, and lives, or an error message if no file exists or the file is corrupted.
     */
    public static String getSaveInfo() {
        if (!saveExists()) {
            return "No saved game found!";
        }

        try {
            // Load the game to get info
            final GameSave gameSave = loadGame();

            if (gameSave != null) {
                // NOTE: SimpleDateFormat is used in the original code but only level/score/lives are returned.
                // Keeping the SimpleDateFormat declaration as it was in the original structure, even if unused in return.
                final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

                return String.format("Level: %d - Score: %d - Lives: %d",
                        gameSave.getLevel(), gameSave.getScore(), gameSave.getLives());
            }
        } catch (final Exception e) {
            // Ignore error for info display purposes, returning a generic error message.
            System.err.println("Error reading save file info: " + e.getMessage());
        }

        return "Corrupted save file!";
    }
}