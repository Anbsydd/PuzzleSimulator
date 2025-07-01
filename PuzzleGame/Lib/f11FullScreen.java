package PuzzleGame.Lib;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public interface f11FullScreen extends KeyListener, frameSizeSet {
    default void f11FullScreen(JFrame frame){
        frame.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }
            @Override
            public void keyPressed(KeyEvent e) {
            }
            @Override
            public void keyReleased(KeyEvent e) {
                final boolean isFullScreen =frame == GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getFullScreenWindow();
                int code = e.getKeyCode();
                if (code == 122) {
                    System.out.println(isFullScreen);
                    if (!isFullScreen) {
                        setFullScreenWindow(true,frame);
                    } else if (isFullScreen){
                        setFullScreenWindow(false,frame);
                    }
                }
            }
        });
    }
    private void setFullScreenWindow(boolean isFullScreen,JFrame frame) {
        if (isFullScreen) {
            GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().setFullScreenWindow(frame);
        } else {
            GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().setFullScreenWindow(null);
        }
    }
}
