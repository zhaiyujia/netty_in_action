package com.netty.action.bytebuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.junit.jupiter.api.Test;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @author zhai
 * @date 2023/3/1 3:46 PM
 * @illustration
 * @slogan: Treat others the way you want to be treated
 * @version:
 */
class TestByteBuf {

    @Test
    public void testGSByteBuf(){
        Charset utf8 = StandardCharsets.UTF_8;
        ByteBuf buf = Unpooled.copiedBuffer("Netty in Action rocks!", utf8);
        System.out.println((char)buf.getByte(0));
        int readIndex = buf.readerIndex();
        int writeIndex = buf.writerIndex();
        buf.setByte(0, (byte)'B');
        System.out.println((char)buf.getByte(0));
        assert readIndex == buf.readerIndex();
        assert writeIndex == buf.writerIndex();
    }

    @Test
    public void testRWByteBuf(){
        Charset utf8 = StandardCharsets.UTF_8;
        ByteBuf buf = Unpooled.copiedBuffer("Netty in Action rocks!", utf8);
        System.out.println((char)buf.getByte(0));
        int readIndex = buf.readerIndex();
        int writeIndex = buf.writerIndex();
        buf.writeByte((byte)'?');
        buf.readByte();
        assert readIndex != buf.readerIndex();
        assert writeIndex != buf.writerIndex();
    }

}
