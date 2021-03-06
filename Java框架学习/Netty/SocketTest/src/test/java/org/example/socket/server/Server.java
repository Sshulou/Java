package org.example.socket.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.InputStream;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.ServerSocket;

public class Server {

    private static final Logger logger = LoggerFactory.getLogger(Server.class);

    public void accept() {
        try {
            /**
             * 基于TCP协议的Socket通信，实现用户登录，服务端
             */
            //1、创建一个服务器端Socket，即ServerSocket，指定绑定的端口，并监听此端口
            ServerSocket serverSocket = new ServerSocket(10086);//1024-65535的某个端口
            //2、调用accept()方法开始监听，等待客户端的连接
            Socket socket = serverSocket.accept();
            //3、获取输入流，并读取客户端信息
            InputStream is = socket.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br =new BufferedReader(isr);
            String info = null;
            while((info = br.readLine()) != null){
                System.out.println("我是服务器，客户端发送消息为：" + info);
            }
            socket.shutdownInput();//关闭输入流
            //4、获取输出流，响应客户端的请求
            OutputStream os = socket.getOutputStream();
            PrintWriter pw = new PrintWriter(os);
            pw.write("欢迎您！");
            pw.flush();

            //5、关闭资源
            pw.close();
            os.close();
            br.close();
            isr.close();
            is.close();
            socket.close();
            serverSocket.close();
        } catch (IOException e) {
            logger.error("occur exception:", e);
        }
    }

    public static void main(String []args) {
        Server server = new Server();
        server.accept();
    }
}
