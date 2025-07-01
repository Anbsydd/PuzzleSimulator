package PuzzleGame.Test;

import PuzzleGame.ui.GameJFrame;
import PuzzleGame.Lib.*;

import java.io.File;

public class TestPicInit {
    public static void main(String[] args) {
        int rows = 4;
        int cols = 4;
        String path = Gets.getMyPath();
        File pic = new File(path+"/PuzzleGame/pictures/0ec3c19d70d938e81ee1bcc3a0f3bc84.jpg");
        new GameJFrame(pic,rows,cols);
    }
}
