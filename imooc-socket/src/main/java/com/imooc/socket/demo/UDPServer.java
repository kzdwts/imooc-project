package com.imooc.socket.demo;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * Created with IntelliJ IDEA.
 *
 * @Description:
 * @author: kangyong
 * @date: 2020/5/9 12:33
 * @version: v1.0
 */
public class UDPServer {

    public static void main(String[] args) throws IOException {
        /**
         * 接收客户端的数据
         */
        // 1、创建服务器端DatagramSocket，指定端口
        DatagramSocket socket = new DatagramSocket(8888);

        // 2、创建数据报，用于接收客户端发送的数据
        // 2.1、创建字节数组，指定接收的数据报的大小
        byte[] data = new byte[1024];
        DatagramPacket packet = new DatagramPacket(data, data.length);

        // 3、接收客户端发送过来的数据
        // 3.1、通过创建新的字符串对象解析数据
        socket.receive(packet);

        // 4、读取数据，输出
        String info = new String(data, 0, packet.getLength());
        System.out.println(info);

        /**
         * 向客户端响应数据
         */
        // 1、获取客户端的地址、端口号、数据
        InetAddress address = packet.getAddress();
        int port = packet.getPort();
        byte[] data2 = "欢迎您，welcome".getBytes();

        // 2、创建数据报，包含响应的数据信息
        DatagramPacket packet2 = new DatagramPacket(data2, data2.length, address, port);

        // 3、响应客户端
        socket.send(packet2);

        // 4、关闭资源
        socket.close();
    }
}
