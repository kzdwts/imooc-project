package com.imooc.socket.demo;

import java.io.*;
import java.net.Socket;

/**
 * Created with IntelliJ IDEA.
 *
 * @Description: 多线程服务端
 * @author: kangyong
 * @date: 2020/4/16 23:23
 * @version: v1.0
 */
public class ServerThread extends Thread {

    private Socket socket = null;

    public ServerThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        InputStream is = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        OutputStream os = null;
        PrintWriter pw = null;

        try {
            // 获取输入流，读取客户端信息
            is = socket.getInputStream();
            isr = new InputStreamReader(is);
            br = new BufferedReader(isr);
            String info = null;
            if ((info = br.readLine()) != null) {
                System.out.println("我是服务端，客户端说：" + info);
            }

            // 向客户端发送消息
            os = socket.getOutputStream();
            pw = new PrintWriter(os);
            pw.write("欢迎你");
            pw.flush();

            socket.shutdownInput();
            socket.shutdownOutput();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (pw != null) {
                    pw.close();
                }
                if (os != null) {
                    os.close();
                }
                if (br != null) {
                    br.close();
                }
                if (isr != null) {
                    isr.close();
                }
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
