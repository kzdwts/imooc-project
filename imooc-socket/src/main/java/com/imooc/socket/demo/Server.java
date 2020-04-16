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

            while (true) {
                // 开启监听
                Socket socket = serverSocket.accept();
                // 调用多线程服务端处理客户端的请求
                ServerThread serverThread = new ServerThread(socket);
                serverThread.start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
