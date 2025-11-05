package com.breakout.managers;

import com.breakout.saves.GameSave;
import java.io.*;
import java.text.SimpleDateFormat;

/**
 * Manages game save and load operations for the Breakout game.
 * Handles serialization of game state to disk and provides utilities
 * for checking, deleting, and displaying save file information.
 *
 * @author Breakout Team
 * @version 1.0
 */
public class SaveManager {
    /** Directory path where save files are stored */
    private static final String SAVE_DIR = "saves/";

    /** File path for the main game save file */
    private static final String SAVE_FILE = SAVE_DIR + "game_save.dat";

    // Static initializer to ensure save directory exists
    static {
        // Create saves directory if it doesn't exist
        File dir = new File(SAVE_DIR);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    /**
     * Saves the current game state to disk.
     * Serializes the GameSave object and writes it to the save file.
     * Automatically sets the save date to the current timestamp.
     *
     * @param gameSave the game state data to be saved
     * @return boolean true if save was successful, false otherwise
     */
    public static boolean saveGame(GameSave gameSave) {
        try {
            // Set current timestamp before saving
            gameSave.setSaveDate(new java.util.Date());

            FileOutputStream fileOut = new FileOutputStream(SAVE_FILE);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(gameSave);
            objectOut.close();
            fileOut.close();

            System.out.println("Game saved successfully!");
            return true;

        } catch (IOException e) {
            System.err.println("Error saving game: " + e.getMessage());
            return false;
        }
    }

    /**
     * Loads a previously saved game state from disk.
     * Deserializes the GameSave object from the save file.
     *
     * @return GameSave the loaded game state, or null if load failed
     */
    public static GameLoadResult loadGame() {
        try {
            File file = new File(SAVE_FILE);
            if (!file.exists()) {
                return new GameLoadResult(null, "No save file found");
            }

            FileInputStream fileIn = new FileInputStream(SAVE_FILE);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            GameSave gameSave = (GameSave) objectIn.readObject();
            objectIn.close();
            fileIn.close();

            System.out.println("Game loaded successfully!");
            return new GameLoadResult(gameSave, "Game loaded successfully");

        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading game: " + e.getMessage());
            return new GameLoadResult(null, "Error loading game: " + e.getMessage());
        }
    }

    /**
     * Checks if a save file exists on disk.
     *
     * @return boolean true if a save file exists, false otherwise
     */
    public static boolean saveExists() {
        File file = new File(SAVE_FILE);
        return file.exists();
    }

    /**
     * Deletes the existing save file from disk.
     * Useful for starting a new game or clearing corrupted saves.
     */
    public static void deleteSave() {
        File file = new File(SAVE_FILE);
        if (file.exists()) {
            file.delete();
            System.out.println("Save game deleted!");
        }
    }

    /**
     * Retrieves information about the existing save file.
     * Provides formatted string with level, score, lives, and save timestamp.
     *
     * @return String formatted save information or error message
     */
    public static String getSaveInfo() {
        if (!saveExists()) {
            return "No saved game found!";
        }

        try {
            GameLoadResult result = loadGame();
            GameSave gameSave = result.getGameSave();

            if (gameSave != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                return String.format("Level: %d - Score: %d - Lives: %d - Saved: %s",
                        gameSave.getLevel(), gameSave.getScore(), gameSave.getLives(),
                        sdf.format(gameSave.getSaveDate()));
            }
        } catch (Exception e) {
            // Ignore error for info display
        }

        return "Corrupted save file!";
    }

    /**
     * Helper class to encapsulate load result with status message.
     * Provides both the loaded GameSave object and a status message.
     */
    public static class GameLoadResult {
        private final GameSave gameSave;
        private final String message;

        public GameLoadResult(GameSave gameSave, String message) {
            this.gameSave = gameSave;
            this.message = message;
        }

        public GameSave getGameSave() {
            return gameSave;
        }

        public String getMessage() {
            return message;
        }

        public boolean isSuccess() {
            return gameSave != null;
        }
    }
}
