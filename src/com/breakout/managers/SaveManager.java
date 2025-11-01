package com.breakout.managers;

import java.io.*;

public class SaveManager {
    private static final String SAVE_FILE = "game_save.dat";

    public static void saveGame(GameSave gameSave) {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(SAVE_FILE))) {
            oos.writeObject(gameSave);
            System.out.println("Game saved successfully!");
        } catch (IOException e) {
            System.err.println("Failed to save game: " + e.getMessage());
        }
    }

    public static GameSave loadGame() {
        File saveFile = new File(SAVE_FILE);
        if (!saveFile.exists()) {
            return null;
        }

        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(SAVE_FILE))) {
            return (GameSave) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Failed to load game: " + e.getMessage());
            return null;
        }
    }

    public static boolean saveExists() {
        return new File(SAVE_FILE).exists();
    }

    public static void deleteSave() {
        File saveFile = new File(SAVE_FILE);
        if (saveFile.exists()) {
            saveFile.delete();
        }
    }

    public static String getSaveInfo() {
        GameSave save = loadGame();
        if (save == null) return null;

        return String.format("Level %d - %s - %d points",
                save.getLevel(), save.getDifficulty(), save.getScore());
    }
}
