package com.qgstudio.view;

import javax.swing.*;
import java.awt.event.*;

public class MainDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JButton 文件Button;
    private JButton 运行Button;
    private JButton 编辑Button;
    private JButton 分析Button;
    private JButton 视图Button;
    private JButton 导航Button;
    private JButton 重构Button;

    public MainDialog() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // 单击十字时调用 onCancel()
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // 遇到 ESCAPE 时调用 onCancel()
        contentPane.registerKeyboardAction(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() {
        // 在此处添加代码
//        dispose();
        System.exit(0);
    }

    private void onCancel() {
        // 必要时在此处添加您的代码
//        dispose();
        System.exit(0);
    }

    public  void init() {
        MainDialog dialog = new MainDialog();
        dialog.setSize(600,400);
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }
}
