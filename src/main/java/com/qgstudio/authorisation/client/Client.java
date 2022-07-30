package com.qgstudio.authorisation.client;

import com.qgstudio.authorisation.util.Declassify;

import java.io.*;
import java.net.Socket;

/**
 * @program: ClientDemo
 * @description:
 * @author: stop.yc
 * @create: 2022-07-29 09:19
 **/
public class Client {
    // 主机地址
    private String host = "localhost";
    // 端口
    private int port = 9900;

    /**
     * 客户端向服务端发送消息
     */
    public boolean sendMessage(String msg) {
        // 响应结果
        StringBuffer result = new StringBuffer();
        BufferedReader br = null;
        InputStream is = null;
        OutputStream os = null;
        PrintWriter pw = null;
        Socket socket = null;
        try {
            // 和服务器创建连接
            socket = new Socket(host,port);
            System.out.println("和服务器已建立连接....");
            // 要发送给服务器的信息
            os = socket.getOutputStream();
            pw = new PrintWriter(os);
            // 给服务端发msg
            pw.write(msg);
            pw.flush();

            socket.shutdownOutput();

            // 从服务器接收的信息
            is = socket.getInputStream();
            br = new BufferedReader(new InputStreamReader(is, "gbk"));
            String info = null;
            while((info = br.readLine())!=null){
                result.append(info);
            }

            System.out.println(result.toString());

            return Declassify.check(result.toString());

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }finally {
            // 关闭流和socket
            try {
                if(br != null) {
                    br.close();
                }
                if(is != null) {
                    is.close();
                }


                if(os != null) {
                    os.close();
                }
                if(pw != null) {
                    pw.close();
                }
                if(socket != null) {
                    socket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
