package com.netty.action.bytebuf;

import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;


/**
 * @author zhai
 * @date 2023/3/1 3:56 PM
 * @illustration
 * @slogan: Treat others the way you want to be treated
 * @version:
 */
public class AllocByteBuf {

    public static void main(String[] args) {
        SocketChannel channel = new NioSocketChannel();
        ByteBufAllocator alloc = channel.alloc();

        ChannelHandlerContext ctx = null;
        ByteBufAllocator alloc1 = ctx.alloc();

    }

}
