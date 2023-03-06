package com.netty.action.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

/**
 * @author zhai
 * @date 2023/2/28 4:26 PM
 * @illustration
 * @slogan: Treat others the way you want to be treated
 * @version:
 */
public class EchoServer {


    public void start() throws Exception {
        EchoServerHandler handler = new EchoServerHandler();
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap(); // 2
            b.group(group)
                    .channel(NioServerSocketChannel.class) // 3  OioServerSocketChannel
                    .localAddress(new InetSocketAddress(8080)) // 4
                    .childHandler(new ChannelInitializer<SocketChannel>() { // 5
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(handler);
                        }
                    });
            ChannelFuture f = b.bind().sync(); // 6
            f.channel().closeFuture().sync(); // 7
        } catch (InterruptedException e) {
            group.shutdownGracefully().sync(); // 8
        }

    }

}
