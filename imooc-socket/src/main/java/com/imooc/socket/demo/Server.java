package com.imooc.socket.demo;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created with IntelliJ IDEA.
 *
 * @Description: 服务端（基于TCP协议的socket通信，实现用户登录）
 * @author: kangyong
 * @date: 2020/4/15 21:50
 * @version: v1.0
 */
public class Server {

    public static void main(String[] args) {
        try {
            // 创建一个服务端Socket
            ServerSocket serverSocket = new ServerSocket(8888);
            // 开启accept监听客户端的连接
            System.out.println("***服务端已开启，等待客户端连接***");
            Socket socket = serverSocket.accept();
            // 获取客户端的输入流
            InputStream is = socket.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String info = null;
            while ((info = br.readLine()) != null) {
                System.out.println("我是服务端，客户端说：" + info);
            }
            socket.shutdownInput();

            // 发送信息给客户端
            OutputStream os = socket.getOutputStream();
            PrintWriter pw = new PrintWriter(os);
            pw.write("欢迎您");
            pw.flush();

            pw.close();
            os.close();
            // 释放资源
            br.close();
            isr.close();
            is.close();
            socket.close();
            serverSocket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
