package com.imooc.socket.demo;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

/**
 * Created with IntelliJ IDEA.
 *
 * @Description:
 * @author: kangyong
 * @date: 2020/5/9 12:33
 * @version: v1.0
 */
public class UDPClient {

    public static void main(String[] args) throws IOException {
        DatagramSocket socket = new DatagramSocket();
        /**
         * 向服务器发送请求
         */
        InetAddress address = InetAddress.getByName("localhost");
        int port = 8888;
        byte[] data = "用户名：wanglili,密码：123456".getBytes();
        DatagramPacket packet = new DatagramPacket(data, data.length, address, port);
        socket.send(packet);

        /**
         * 接收服务器响应的数据
         */
        byte[] data2 = new byte[1024];
        DatagramPacket packet2 = new DatagramPacket(data2, data2.length);
        socket.receive(packet2);
        String info = new String(data2, 0, packet2.getLength());
        System.out.println(info);

        socket.close();
    }
}
