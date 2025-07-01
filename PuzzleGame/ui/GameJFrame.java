package PuzzleGame.ui;


import PuzzleGame.Lib.frameSizeSet;
import PuzzleGame.Lib.f11FullScreen;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class GameJFrame extends JFrame implements frameSizeSet,java.awt.event.KeyListener, ActionListener, f11FullScreen {
    int rows;
    int cols;
    int[] arr;
    int[][] staIndex;
    int[][] index;
    int picWid;
    int picHei;
    int x;
    int y;
    int num;
    int frameWid;
    int frameHei;
    int xLocation;
    int yLocation;
    int step;
    int lookTimes;
    BufferedImage image;
    BufferedImage[] pics;
    JLabel jLabel;
    File pic;
    ComponentAdapter ca = new ComponentAdapter() {
        int lastTimingWidth;
        int lastTimingHeight;
        public void componentResized(java.awt.event.ComponentEvent evt) {
            if (getWidth() != lastTimingWidth || getHeight() != lastTimingHeight) {
                int timingWidth = getWidth();
                int timingHeight = getHeight();
                picWid = timingWidth * 3 / 4;
                picHei = timingHeight * 3 / 4;
                pictureInit(pic);
                refreshImage();
                lastTimingWidth = timingWidth;
                lastTimingHeight = timingHeight;
            }
        }
    };
    JMenuItem replay = new JMenuItem("重新开始");
    JMenuItem relog = new JMenuItem("重新登录");
    JMenuItem close = new JMenuItem("关闭游戏");
    JMenuItem account = new JMenuItem("公众号");
    public GameJFrame(File pic, int rows, int cols) {
        this.pic = pic;
        this.rows = rows;
        this.cols = cols;
        num = rows * cols;
        this.picWid = frameSizeSet.WIDTH * 3/4;
        this.picHei = frameSizeSet.HEIGHT * 3/4;
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        enableInputMethods(false);
        pictureInit(pic);
        initJMenu();
        initJMenuBar();
        initArr();
        initImage();
        f11FullScreen(this);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
    }
    private void initJMenu() {
        // 设置窗口大小
        this.setSize(frameSizeSet.WIDTH, frameSizeSet.HEIGHT);
        // 设置窗口标题
        this.setTitle("拼图");
        // 设置窗口居中
        this.setLocationRelativeTo(null);
        // 设置窗口关闭时的操作
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.addKeyListener(this);
        this.setVisible(true);
        this.addComponentListener(ca);
    }
    private void initJMenuBar() {
        JMenuBar mb = new JMenuBar();
        this.setJMenuBar(mb);

        JMenu function = new JMenu("功能");
        function.add(replay);
        function.add(relog);
        function.add(close);
        replay.addActionListener(this);
        relog.addActionListener(this);
        close.addActionListener(this);
        mb.add(function);
        JMenu aboutUs = new JMenu("关于我们");
        aboutUs.add(account);
        account.addActionListener(this);
        mb.add(aboutUs);

    }
    private void initArr() {
        Random random = new Random();
        int temp;
        arr = new int[num];
        index = new int[rows][cols];
        staIndex = new int[rows][cols];
        for (int i = 0; i < num; i++) {
            arr[i] = i;
        }
        for (int i = 0; i < rows; i++) {
            System.arraycopy(arr, i * cols, staIndex[i], 0, cols);
        }
        // 遍历数组，将数组中的元素随机交换位置
        for (int i = 0; i < arr.length; i++) {
            int index = random.nextInt(arr.length);
            temp = arr[i];
            arr[i] = arr[index];
            arr[index] = temp;
        }
        for (int i = 0; i < rows; i++) {
            if (cols >= 0) System.arraycopy(arr, i * cols, index[i], 0, cols);
        }

    }
    private void initImage() {
        try {
            addImage();
        } catch (Exception e){
            System.out.println("图片初始化失败！");
        }
        // 设置窗口可见
        this.setVisible(true);
    }
    private void addImage (){
        JLabel stepLabel = new JLabel("步数：" + step);
        stepLabel.setBounds(50, 30, 100, 20);
        stepLabel.setFont(new Font("微软雅黑", Font.BOLD, 20));
        this.getContentPane().add(stepLabel);
        if (lookTimes != 0) {
            JLabel lookTimesLabel = new JLabel("你已经看了" + lookTimes + "次了！");
            lookTimesLabel.setBounds(50, 60, 1000, 20);
            lookTimesLabel.setFont(new Font("微软雅黑", Font.BOLD, 20));
            this.getContentPane().add(lookTimesLabel);
        }
        if(isVictory()){
            JLabel vicLabel = new JLabel("Complete!");
            vicLabel.setBounds(getWidth() / 3, 0, getWidth(), getHeight());
            vicLabel.setFont(new Font("微软雅黑", Font.BOLD, 80));
            this.add(vicLabel, BorderLayout.NORTH);
            this.getContentPane().add(vicLabel);
        }
        int hei = picHei/this.rows;
        int wid = picWid/this.cols;
        xLocation = (this.getWidth() - picWid)/2;
        yLocation = (this.getHeight() - picHei)/2;
        pics[this.num-1] = null;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                // 创建一个ImageIcon对象，用于存储图片
                if (pics[index[i][j]] == null){
                    x=i;
                    y=j;
                }else{
                    ImageIcon icon = new ImageIcon(pics[index[i][j]]);
                    // 创建一个JLabel对象，用于显示图片
                    jLabel = new JLabel(icon);
                    // 设置JLabel的位置和大小
                    jLabel.setBounds(wid * j + xLocation, hei * i + yLocation, wid, hei);
                    jLabel.setBorder(new BevelBorder(BevelBorder.LOWERED));
                    this.getContentPane().add(jLabel);
                }
            }
        }
    }
    private void refreshImage() {
        this.getContentPane().removeAll();
        frameHei = this.getHeight();
        frameWid = this.getWidth();
        addImage();
        this.getContentPane().repaint();
    }
    public void pictureInit(File file) {

        // 调用init方法初始化图片
        image = initFile(file);
        // 如果图片初始化失败，输出错误信息并返回
        if(pic==null) {
            System.out.println("图片初始化失败！");
            return;
        }
        try {
            // 调用partition方法分割图片
            this.pics = partition(image);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private BufferedImage[] partition(BufferedImage image) throws IOException {
        // 计算分割后的图片数量
        int chunks = this.rows * this.cols;
        // 计算每个小图的宽度和高度
        int chunkWidth = image.getWidth() / cols;
        int chunkHeight = image.getHeight() / this.rows;
        //大图中的一部分
        int count = 0;
        // 创建一个BufferedImage数组，用于存储分割后的图片
        BufferedImage[] imgs = new BufferedImage[chunks];
        // 遍历每个小图，进行分割
        for (int x = 0; x < this.rows; x++) {
            for (int y = 0; y < this.cols; y++) {
                //设置小图的大小和类型
                imgs[count] = new BufferedImage(chunkWidth, chunkHeight, image.getType());
                //写入图像内容
                Graphics2D gr = imgs[count++].createGraphics();
                gr.drawImage(image, 0, 0, chunkWidth, chunkHeight, chunkWidth * y, chunkHeight * x, chunkWidth * y + chunkWidth, chunkHeight * x + chunkHeight, null);
                gr.dispose();
            }
        }
        return imgs;
    }
    public BufferedImage initFile(File file) {
        BufferedImage originalImage;
        try {
            // 读取图片文件
            originalImage = ImageIO.read(file);
        } catch (IOException e) {
            System.out.println("不是图片格式！");
            return null;
        }
        // 设置缩略图的大小
        // 创建一个BufferedImage对象，用于存储缩略图
        BufferedImage thumbnail = new BufferedImage(this.picWid, this.picHei, BufferedImage.TYPE_INT_RGB);
        // 创建一个Graphics2D对象，用于绘制缩略图
        Graphics2D g2d = thumbnail.createGraphics();
        // 设置插值算法
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        // 绘制缩略图
        g2d.drawImage(originalImage.getScaledInstance(this.picWid, this.picHei, Image.SCALE_SMOOTH), 0, 0, null);
        // 释放资源
        g2d.dispose();
        return thumbnail;
    }
    @Override
    public void keyTyped(KeyEvent e) {

    }
    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if (!isVictory()) {
            if (code == 65) {
                this.getContentPane().removeAll();
                JLabel all = new JLabel(new ImageIcon(image));
                all.setBounds(xLocation, yLocation, picWid, picHei);
                this.getContentPane().add(all);
                this.getContentPane().repaint();
            }
        }
    }
    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        int num;
        System.out.println(code);
        if (!isVictory()) {
            if (code == 39) {
                if (y != cols - 1) {
                    System.out.println("向左移动");
                    num = index[x][y];
                    index[x][y] = index[x][y + 1];
                    index[x][y + 1] = num;
                    y++;
                    step++;
                    refreshImage();
                }
            } else if (code == 40) {
                if (x != rows - 1) {
                    System.out.println("向上移动");
                    num = index[x][y];
                    index[x][y] = index[x + 1][y];
                    index[x + 1][y] = num;
                    x++;
                    step++;
                    refreshImage();
                }
            } else if (code == 37) {
                if (y != 0) {
                    System.out.println("向右移动");
                    num = index[x][y];
                    index[x][y] = index[x][y - 1];
                    index[x][y - 1] = num;
                    y--;
                    step++;
                    refreshImage();
                }
            } else if (code == 38) {
                if (x != 0) {
                    System.out.println("向下移动");
                    num = index[x][y];
                    index[x][y] = index[x - 1][y];
                    index[x - 1][y] = num;
                    x--;
                    step++;
                    refreshImage();
                }
            }else if (code == 68) {
                index = staIndex.clone();
                refreshImage();
            }
        }
        if (code == 65) {
            if (!isVictory()) {
                lookTimes++;
            }
            refreshImage();
        }
    }
    private boolean isVictory() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (index[i][j] != staIndex[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }
    @Override
    public void f11FullScreen(JFrame frame) {
        f11FullScreen.super.f11FullScreen(this);
        refreshImage();
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == relog) {
            //关闭当前页面
            setVisible(false);
            //加载登录界面
            new LoginJFrame();
        } else if (source == replay) {
            step = 0;
            initArr();
            refreshImage();
        } else if (source == account) {
            JDialog jDialog = new JDialog();
            //设置大小size
            jDialog.setSize(400, 400);
            //设置弹窗置顶
            jDialog.setAlwaysOnTop(true);
            //设置弹窗居中
            jDialog.setLocationRelativeTo(null);
            //设置弹窗不关闭无法操纵其他界面
            jDialog.setModal(true);
            jDialog.setTitle("记得投币偶");
            JLabel aboutLabel = new JLabel(
                    new ImageIcon("image\\about.png")
            );
            aboutLabel.setBounds(0, 0, 317, 318);
            jDialog.getContentPane().add(aboutLabel);
            //让弹窗显示出来
            jDialog.setVisible(true);
        } else if (source == close) {
            System.exit(0);
        }
    }
}