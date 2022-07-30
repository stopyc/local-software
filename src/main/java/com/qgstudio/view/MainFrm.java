package com.qgstudio.view;


import com.qgstudio.authorisation.service.CheckService;

import javax.swing.*;
import java.io.IOException;

/**
 * @program: local-software
 * @description:
 * @author: stop.yc
 * @create: 2022-07-30 09:25
 **/
public class MainFrm {
    private JPanel platformPanel;
    private JPanel panel1;

    /**
     * @Description: 初始化页面
     * @Param: []
     * @return: void
     * @Author: stop.yc
     * @Date: 2022/3/18
     */
    public void init()  {
        JFrame frame = new JFrame("MainFrm");
        frame.setContentPane(new MainFrm().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 440);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);




        //需要进行授权验证
        boolean check = new CheckService().check();


        if (check) {
            JOptionPane.showMessageDialog(null,"本地软件授权校验成功");
            frame.setVisible(false);
            new MainDialog().init();
        }else {
            JOptionPane.showMessageDialog(null,"本地软件授权校验失败");
            int i = JOptionPane.showConfirmDialog(null, "是否前往管理平台购买许可?您将可以使用本软件的完整功能.");
            if (i == JOptionPane.YES_OPTION) {
                //重写run方法
                new Thread(() -> {
                    //构造命令
                    String cmd = "cmd.exe /c start ";

                    //构造本地文件路径或者网页URL
                    String file = "http://106.13.18.48:9876";
//                            String file = "C:/Users/Wentasy/Desktop/core_java_3_api/index.html";

                    try {
                        //执行操作
                        Runtime.getRuntime().exec(cmd + file);
                    } catch (IOException ignore) {
                        //打印异常
                        ignore.printStackTrace();
                    }
                }).start();
            }else {
                JOptionPane.showMessageDialog(null,"本应用即将关闭");
                System.exit(0);
            }

        }
    }
}
