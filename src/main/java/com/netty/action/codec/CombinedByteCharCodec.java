package com.netty.action.codec;

import com.netty.action.decode.ToIntegerDecoder;
import com.netty.action.encode.ShortToByteEncoder;
import io.netty.channel.CombinedChannelDuplexHandler;

/**
 * @author zhai
 * @date 2023/3/6 11:34 AM
 * @illustration
 * @slogan: Treat others the way you want to be treated
 * @version:
 */
public class CombinedByteCharCodec extends CombinedChannelDuplexHandler<ToIntegerDecoder, ShortToByteEncoder> {

    public CombinedByteCharCodec() {
        super(new ToIntegerDecoder(), new ShortToByteEncoder());
    }
}
