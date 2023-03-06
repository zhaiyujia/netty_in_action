package com.netty.action.bytebuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @author zhai
 * @date 2023/3/1 3:29 PM
 * @illustration
 * @slogan: Treat others the way you want to be treated
 * @version:
 */
public class CopyByteBuf {

    public static void main(String[] args) {
        Charset utf8 = StandardCharsets.UTF_8;
        ByteBuf buf = Unpooled.copiedBuffer("Netty in Action rocks!", utf8);
        ByteBuf copy = buf.copy(0, 15);
        buf.setByte(0, (byte) 'J');
        System.out.println(buf.getByte(0) == copy.getByte(0));
    }

}
