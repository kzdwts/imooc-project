package com.imooc.socket.demo;

import java.io.*;
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
            pw.write("用户名:wanglili;密码:654321");
            pw.flush();
            socket.shutdownOutput();

            // 接受客户端发送过来的信息
            InputStream is = socket.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String info = null;
            while ((info = br.readLine()) != null) {
                System.out.println("我是客户端，服务端说：" + info);
            }
            socket.shutdownInput();

            // 关闭资源
            br.close();
            isr.close();
            is.close();
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
