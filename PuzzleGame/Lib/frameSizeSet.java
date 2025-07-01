package PuzzleGame.Lib;

import java.awt.*;
public interface frameSizeSet {
    int WIDTH = 1600;
    int HEIGHT = 900;
    GraphicsDevice graphicsDevice = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
    default void setSize(Frame frame) {
        if (graphicsDevice.isFullScreenSupported()) {
            frame.setUndecorated(true);
            graphicsDevice.setFullScreenWindow(frame);
        } else {
            frame.setSize(WIDTH, HEIGHT);
            frame.setVisible(true);
        }
    }
    default void draw(Graphics g, int screenWidth, int screenHeight) {
        int boxSize = (int)(screenWidth * 0.05); // 根据屏幕宽度调整元素大小
        g.fillRect(screenWidth / 2 - boxSize / 2, screenHeight / 2 - boxSize / 2, boxSize, boxSize);
    }
}
