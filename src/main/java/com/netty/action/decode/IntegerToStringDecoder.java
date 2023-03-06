package com.netty.action.decode;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.List;

/**
 * @author zhai
 * @date 2023/3/6 11:00 AM
 * @illustration
 * @slogan: Treat others the way you want to be treated
 * @version:
 */
public class IntegerToStringDecoder extends MessageToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, Object msg, List out) throws Exception {
        out.add(String.valueOf(msg));
    }
}
