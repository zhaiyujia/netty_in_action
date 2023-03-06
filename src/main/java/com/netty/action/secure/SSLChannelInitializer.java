package com.netty.action.secure;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.ssl.SslHandler;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;

/**
 * @author zhai
 * @date 2023/3/6 2:08 PM
 * @illustration
 * @slogan: Treat others the way you want to be treated
 * @version:
 */
public class SSLChannelInitializer extends ChannelInitializer<Channel> {

    private final SSLContext context;
    private final boolean startTls;

    public SSLChannelInitializer(SSLContext context,
                                 boolean startTls) {
        this.context = context;
        this.startTls = startTls;
    }

    @Override
    protected void initChannel(Channel ch) throws Exception {
        // SSLEngine engine = context.newEngine(ch.alloc());
        SSLEngine engine = context.createSSLEngine();
        ch.pipeline()
                .addFirst("ssl", new SslHandler(engine, startTls));
    }
}
