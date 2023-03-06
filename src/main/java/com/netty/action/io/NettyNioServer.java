package com.netty.action.io;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.oio.OioEventLoopGroup;
import io.netty.channel.sctp.nio.NioSctpServerChannel;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.oio.OioServerSocketChannel;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

/**
 * @author zhai
 * @date 2023/3/1 11:08 AM
 * @illustration
 * @slogan: Treat others the way you want to be treated
 * @version:
 */
public class NettyNioServer {

    public void server(int port) throws Exception {
        ByteBuf buf = Unpooled.unreleasableBuffer(Unpooled.copiedBuffer("Hi!\r\n", Charset.forName("UTF-8")));
        EventLoopGroup group = new NioEventLoopGroup();
        ServerBootstrap b = new ServerBootstrap();
        b.group(group)
                .channel(NioSctpServerChannel.class)
                .localAddress(new InetSocketAddress(port))
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                            @Override
                            public void channelInactive(ChannelHandlerContext ctx) throws Exception {
                                ctx
                                        .writeAndFlush(buf.duplicate())
                                        .addListener(ChannelFutureListener.CLOSE);
                            }
                        });
                    }
                });
        ChannelFuture f = b.bind().sync();
        f.channel().closeFuture().sync();
        group.shutdownGracefully().sync();

    }


}
