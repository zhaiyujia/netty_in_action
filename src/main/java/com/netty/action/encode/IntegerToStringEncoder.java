package com.netty.action.encode;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

/**
 * @author zhai
 * @date 2023/3/6 11:11 AM
 * @illustration
 * @slogan: Treat others the way you want to be treated
 * @version:
 */
public class IntegerToStringEncoder extends MessageToMessageEncoder<Integer> {

    @Override
    protected void encode(ChannelHandlerContext ctx, Integer msg, List<Object> out) throws Exception {
        out.add(String.valueOf(msg));
    }
}
