package com.netty.action.bytebuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * @author zhai
 * @date 2023/3/1 2:58 PM
 * @illustration
 * @slogan: Treat others the way you want to be treated
 * @version:
 */
public class TestByteBuf {

    public static void main(String[] args) {
        ByteBuf buf = Unpooled.buffer();
        for (int i = 0; i < buf.capacity(); i++) {
            byte b = buf.getByte(i);
            System.out.println((char) b);
        }
    }

}
