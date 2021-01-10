package org.example.netty.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;


public class Client {
    private String host;
    private int port;
    private String message;

    public Client(String host, int port, String message) {
        this.host = host;
        this.port =port;
        this.message = message;
    }

    private void send() throws InterruptedException {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            //创建客户端启动引导/辅助类：Bootstrap
            Bootstrap b = new Bootstrap();
            //指定线程模型
            b.group(group)
                    //指定 IO 模型
                    //和 BIO 编程模型中 Socket相对应
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline p = ch.pipeline();
                            // 这里可以自定义消息的业务处理逻辑
                            p.addLast(new LineBasedFrameDecoder(1024)).
                                    addLast(new ClientHandler(message));
                        }
                    });
            // 尝试建立连接
            ChannelFuture f = b.connect(host, port).addListener(future -> {
                if (future.isSuccess()) {
                    System.out.println("连接成功!");
                } else {
                    System.err.println("连接失败!");
                }
            }).sync();
            // 等待连接关闭
            f.channel().closeFuture().sync();
        } finally {
            // 优雅关闭相关线程组资源
            group.shutdownGracefully();
        }
    }
    public static void main(String[] args) throws Exception {
        new Client("127.0.0.1", 8080, "你好,你真帅啊！哥哥！\n").send();
    }
}
