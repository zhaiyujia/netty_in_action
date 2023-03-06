package com.netty.action.delimited;

import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.LineBasedFrameDecoder;

/**
 * @author zhai
 * @date 2023/3/6 3:00 PM
 * @illustration
 * @slogan: Treat others the way you want to be treated
 * @version:
 */
public class LineBasedHandlerInitializer extends ChannelInitializer<Channel> {

    @Override
    protected void initChannel(Channel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(
                new LineBasedFrameDecoder(64 * 1024),
                new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()),
                new FrameHandler()
        );
    }

    public static final class FrameHandler extends SimpleChannelInboundHandler<ByteBuf> {
        @Override
        protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {

        }
    }
}
