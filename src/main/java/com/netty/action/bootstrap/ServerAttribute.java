package com.netty.action.bootstrap;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOption;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;

import java.net.InetSocketAddress;

/**
 * @author zhai
 * @date 2023/3/6 10:26 AM
 * @illustration
 * @slogan: Treat others the way you want to be treated
 * @version:
 */
public class ServerAttribute {

    public static void main(String[] args) {

        final AttributeKey<Integer> id = new AttributeKey<>("ID");
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(new NioEventLoopGroup())
                .channel(NioSocketChannel.class)
                .handler(new SimpleChannelInboundHandler<ByteBuf>() {
                    @Override
                    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
                        Integer idValue = ctx.channel().attr(id).get();
                        System.out.println("idValue:" + idValue);
                    }

                    @Override
                    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
                        System.out.println("Received data");
                    }
                });
        bootstrap.option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000);

        bootstrap.attr(id, 123456);
        ChannelFuture future = bootstrap.connect(new InetSocketAddress("www.manning.com", 80));
        future.syncUninterruptibly();

    }

}
