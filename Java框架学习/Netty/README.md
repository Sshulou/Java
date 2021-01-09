# netty

## Socket

### Socket通信的步骤：

-   ​	创建ServerSocket(服务器端)和Socket(客户端)
-   ​	打开连接到Socket的输入输出流
-   ​	按照协议对Socket进行读写操作
-   ​	关闭输入输出流、关闭Socket

### 服务器端

-   ​	创建ServerSocket对象，绑定监听端口
-   ​	通过accept()方法监听客户端请求
-   ​	连接建立后通过输入流获取客户端发送的请求信息
-   ​	通过输出流向客户端发送响应信息
-   ​	关闭资源

```java
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
```



### 客户端

-   ​	创建Socket对象，指定需要连接的服务器的地址和端口号
-   ​	连接建立后，通过输出流向服务器端发送请求信息
-   ​	通过输入流获取服务器响应信息
-   ​	关闭资源

```java
//1、创建客户端Socket，指定服务器地址和端口
Socket socket = new Socket("localhost",10086);
//2、获取输出流，向服务器端发送信息
OutputStream os = socket.getOutputStream();//字节输出流
PrintWriter pw = new PrintWriter(os);//将输出流包装成打印流
String sendinfo = "用户名：admin；密码：123";
pw.write(sendinfo);
pw.flush();
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
```