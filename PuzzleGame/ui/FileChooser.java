package PuzzleGame.ui;

import Anbsydd.Anbsydd;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.ActionEvent;

public class FileChooser extends JFrame {
    private JLabel pathLabel;
    public void FileSelector() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            SwingUtilities.updateComponentTreeUI(this);
        } catch (Exception e) {
            e.printStackTrace();
        }

        setTitle("文件选择器");
        setSize(550, 180);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        setLocationRelativeTo(null);

        JPanel contentPane = new JPanel(new BorderLayout(10, 10));
        contentPane.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        add(contentPane);

        JButton selectButton = new JButton("选择文件");
        selectButton.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        selectButton.setFocusPainted(false);
        selectButton.setBackground(new Color(0, 120, 215));
        selectButton.setForeground(Color.WHITE);
        selectButton.setPreferredSize(new Dimension(120, 30));

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        topPanel.add(selectButton);

        pathLabel = new JLabel("未选择文件", JLabel.CENTER);
        pathLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        pathLabel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(212, 212, 212)),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        pathLabel.setBackground(Color.WHITE);
        pathLabel.setOpaque(true);

        contentPane.add(topPanel, BorderLayout.NORTH);
        contentPane.add(pathLabel, BorderLayout.CENTER);

        selectButton.addActionListener((ActionEvent e) -> {
            JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView());
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            fileChooser.setDialogTitle("选择文件");
            fileChooser.setApproveButtonText("选择");
            fileChooser.setApproveButtonToolTipText("确认选择文件");

            int result = fileChooser.showOpenDialog(this);

            if (result == JFileChooser.APPROVE_OPTION) {
                pathLabel.setText("<html>" + fileChooser.getSelectedFile().getAbsolutePath() + "</html>");
            } else {
                pathLabel.setText("已取消选择");
            }
        });
    }

    public FileChooser() {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ex) {
                initUI();
            }
            new Anbsydd.FileChooser().setVisible(true);
        });
    }
    // 初始化界面
    private void initUI() {
        // 创建一个按钮
        JButton selectFolderBtn = new JButton("Select Folder");
        // 为按钮添加点击事件
        selectFolderBtn.addActionListener(e -> showFileChooser(1));
        // 创建布局
        createLayout(selectFolderBtn);
        // 设置窗口标题
        setTitle("FileChooser");
        // 设置窗口大小
        setSize(400, 300);
        // 设置窗口位置
        setLocationRelativeTo(null);
        // 设置窗口关闭操作
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    // 创建布局
    private void createLayout(JButton... arg) {
        // 设置窗口布局为BorderLayout
        getContentPane().setLayout(new BorderLayout());
        // 将按钮添加到窗口中央
        getContentPane().add(arg[0], BorderLayout.CENTER);
    }
    // 显示文件选择器
    private void showFileChooser(int i) {
        // 创建一个文件选择器
        JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        // 设置文件选择器的文件选择模式为只选择文件夹
        if(i == 0) {
            fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        }else if(i == 1){
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        }else if(i == 2){
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        }

        // 显示文件选择器
        int returnValue = fileChooser.showOpenDialog(null);
        // 如果用户选择了文件夹
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            // 获取用户选择的文件夹
            java.io.File selectedFile = fileChooser.getSelectedFile();
            // 输出用户选择的文件夹路径
            System.out.println("所选目录：" + selectedFile.getAbsolutePath());
        } else if (returnValue == JFileChooser.CANCEL_OPTION) {
            System.out.println("用户取消了文件选择。");
        }
    }
}
