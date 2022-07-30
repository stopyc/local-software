package com.qgstudio.authorisation.client;

import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * @program: ClientDemo
 * @description:
 * @author: stop.yc
 * @create: 2022-07-29 09:19
 **/
public class TaskClient {

    public boolean check (int software_id,int version_id,int function_id) {

        try {
            Desktop.getDesktop().open(new File("E:\\java_exe\\ClientDemo\\ClientDemo.exe"));
            System.out.println("正在启动管理平台....");
            Thread.sleep(1000);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new Client().sendMessage(software_id + "&" + version_id + "&" + function_id);
    }
}
