package com.netty.action.decode;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

/**
 * @author zhai
 * @date 2023/3/6 10:50 AM
 * @illustration
 * @slogan: Treat others the way you want to be treated
 * @version:
 */
public class ToIntegerDecoder2 extends ReplayingDecoder<Void> {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
       out.add(in.readInt());
    }
}
