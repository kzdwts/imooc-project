package com.imooc.socket.demo;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created with IntelliJ IDEA.
 *
 * @Description: 客户端
 * @author: kangyong
 * @date: 2020/4/15 22:40
 * @version: v1.0
 */
public class Client {

    public static void main(String[] args) {
        try {
            // 创建客户端socket
            Socket socket = new Socket("127.0.0.1", 8888);
            // 获取输出流，向服务端发送消息
            OutputStream os = socket.getOutputStream();
            PrintWriter pw = new PrintWriter(os);
            pw.write("用户名:wanglili;密码:123456");
            pw.flush();

            // 关闭资源
            pw.close();
            os.close();
            socket.close();

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
