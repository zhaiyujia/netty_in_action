package com.netty.action.encode;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author zhai
 * @date 2023/3/6 11:08 AM
 * @illustration
 * @slogan: Treat others the way you want to be treated
 * @version:
 */
public class ShortToByteEncoder extends MessageToByteEncoder<Short> {

    @Override
    protected void encode(ChannelHandlerContext ctx, Short msg, ByteBuf out) throws Exception {
        out.writeShort(msg);
    }
}
