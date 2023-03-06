package com.netty.action.delimited;

import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * @author zhai
 * @date 2023/3/6 3:25 PM
 * @illustration
 * @slogan: Treat others the way you want to be treated
 * @version:
 */
public class LengthBasedInitializer extends ChannelInitializer<Channel> {

    @Override
    protected void initChannel(Channel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new LengthFieldBasedFrameDecoder(64 * 1024, 0, 8));
        pipeline.addLast(new SimpleChannelInboundHandler<ByteBuf>() {
            @Override
            protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
                
            }
        });
    }


}
