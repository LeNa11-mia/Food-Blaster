package com.breakout.managers;

import com.breakout.saves.GameSave;
import java.io.*;
import java.text.SimpleDateFormat;

public class SaveManager {
    private static final String SAVE_DIR = "saves/";
    private static final String SAVE_FILE = SAVE_DIR + "game_save.dat";

    static {
        // Tạo thư mục saves nếu chưa tồn tại
        File dir = new File(SAVE_DIR);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    public static boolean saveGame(GameSave gameSave) {
        try {
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

    public static GameSave loadGame() {
        try {
            File file = new File(SAVE_FILE);
            if (!file.exists()) {
                return null;
            }

            FileInputStream fileIn = new FileInputStream(SAVE_FILE);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            GameSave gameSave = (GameSave) objectIn.readObject();
            objectIn.close();
            fileIn.close();

            System.out.println("Game loaded successfully!");
            return gameSave;

        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading game: " + e.getMessage());
            return null;
        }
    }

    public static boolean saveExists() {
        File file = new File(SAVE_FILE);
        return file.exists();
    }

    public static void deleteSave() {
        File file = new File(SAVE_FILE);
        if (file.exists()) {
            file.delete();
            System.out.println("Save game deleted!");
        }
    }

    public static String getSaveInfo() {
        if (!saveExists()) {
            return "No saved game found!";
        }

        try {
            GameSave gameSave = loadGame();
            if (gameSave != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                return String.format("Level: %d - Score: %d - Lives: %d",
                        gameSave.getLevel(), gameSave.getScore(), gameSave.getLives());
            }
        } catch (Exception e) {
            // Ignore error for info display
        }

        return "Corrupted save file!";
    }
}
