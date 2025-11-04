package com.breakout.managers;

import com.breakout.config.GameConfig;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Quản lý việc tải và phát âm thanh.
 */
public class SoundManager {
    private static Clip brickHitClip;
    private static Clip wallHitClip;

    // Thread pool với số luồng cố định
    private static final ExecutorService soundPool = Executors.newFixedThreadPool(2);

    /**
     * Tải tất cả âm thanh cần thiết khi game khởi động.
     */
    public static void loadSounds() {
        try {
            // Tải âm thanh va chạm gạch
            brickHitClip = loadClip(GameConfig.BRICK_HIT_SOUND_PATH);
            wallHitClip = loadClip(GameConfig.WALL_HIT_SOUND_PATH);
        } catch (Exception e) {
            System.err.println("Lỗi tải âm thanh: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Tải một Clip từ đường dẫn.
     */
    public static Clip loadClip(String path) throws LineUnavailableException, IOException, UnsupportedAudioFileException {
        // Cần đảm bảo đường dẫn File là chính xác trong môi trường của bạn
        File audioFile = new File(path);

        if (!audioFile.exists()) {
            System.err.println("Không tìm thấy file âm thanh: " + path);
            return null;
        }

        AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
        Clip clip = AudioSystem.getClip();
        clip.open(audioStream);
        return clip;
    }

    /**
     * Phát âm thanh va chạm gạch.
     */
    public static void playBrickHitSound() {
        if (brickHitClip != null) {
            // Reset clip về đầu
            soundPool.submit(() -> { // Giao công việc cho thread pool
                synchronized (brickHitClip) { // Chỉ 1 luồng được truy cập đến brickHitClip -> tránh xung đột dữ liệu
                    brickHitClip.setFramePosition(0);
                    // Dừng nếu đang phát (để phát lại nhanh)
                    if (brickHitClip.isRunning()) {
                        brickHitClip.stop();
                    }
                    // Phát âm thanh
                    brickHitClip.start();
                }
            });

        }
    }

    /**
     * Phát âm thanh va chạm tường.
     */
    public static void playWallHitSound() {
        if (wallHitClip != null) {
            soundPool.submit(() -> {
                synchronized (wallHitClip) {
                    // Reset clip về đầu
                    wallHitClip.setFramePosition(0);
                    // Dừng nếu đang phát (để phát lại nhanh)
                    if (wallHitClip.isRunning()) {
                        wallHitClip.stop();
                    }
                    // Phát âm thanh
                    wallHitClip.start();
                }
            });
        }
    }

    // Thêm các phương thức cho âm thanh khác (Menu Click, Game Over,...)
}