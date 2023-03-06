package com.netty.action.bytebuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @author zhai
 * @date 2023/3/1 3:23 PM
 * @illustration
 * @slogan: Treat others the way you want to be treated
 * @version:
 */
public class SliceByteBuf {

    public static void main(String[] args) {
        Charset utf8 = StandardCharsets.UTF_8;
        ByteBuf buf = Unpooled.copiedBuffer("Netty in Action rocks!", utf8);
        ByteBuf slice = buf.slice(0, 15);
        System.out.println(slice.toString(utf8));
        buf.setByte(0, (byte)'J');
        assert buf.getByte(0) == slice.getByte(0);
    }

}
