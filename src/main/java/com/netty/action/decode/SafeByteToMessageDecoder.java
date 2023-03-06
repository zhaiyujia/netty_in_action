package com.netty.action.decode;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.TooLongFrameException;

import java.util.List;

/**
 * @author zhai
 * @date 2023/3/6 11:03 AM
 * @illustration
 * @slogan: Treat others the way you want to be treated
 * @version:
 */
public class SafeByteToMessageDecoder extends ByteToMessageDecoder {

    private static final Long MAX_FRAME_SIZE = 1200000L;

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        int readable = in.readableBytes();
        if (readable > MAX_FRAME_SIZE) {
            in.skipBytes(readable);
            throw new TooLongFrameException("Frame to big!");
        }
    }
}
