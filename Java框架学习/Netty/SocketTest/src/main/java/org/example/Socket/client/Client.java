package org.example.Socket.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.InputStream;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

public class Client {

    private static final Logger logger = LoggerFactory.getLogger(Client.class);

    public void send() {
        try {
            //客户端
            //1、创建客户端Socket，指定服务器地址和端口
            Socket socket = new Socket("localhost",10086);
            //2、获取输出流，向服务器端发送信息
            OutputStream os = socket.getOutputStream();//字节输出流
            PrintWriter pw = new PrintWriter(os);//将输出流包装成打印流
            String sendinfo = "用户名：admin；密码：123";
            pw.write(sendinfo);
            pw.flush();
            System.out.println("客户端发送信息：" + sendinfo);
            socket.shutdownOutput();//关闭输出流
            //3、获取输入流，并读取服务器端的响应信息
            InputStream is = socket.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String info = null;
            while((info = br.readLine()) != null){
                System.out.println("我是客户端，服务器发送信息为：" + info);
            }

            //4、关闭资源
            br.close();
            is.close();
            pw.close();
            os.close();
            socket.close();
        } catch (IOException e) {
            logger.error("occur exception:", e);
        }
    }

    public static void main(String []args) {
        Client client = new Client();
        client.send();
    }
}
