package com.netty.action.eventloop;

import io.netty.channel.Channel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.ScheduledFuture;

import java.util.concurrent.TimeUnit;

/**
 * @author zhai
 * @date 2023/3/2 4:31 PM
 * @illustration
 * @slogan: Treat others the way you want to be treated
 * @version:
 */
public class ScheduledEventLoop {

    public static void main(String[] args) {
        Channel ch = new NioSocketChannel();
        ScheduledFuture<?> schedule = ch.eventLoop().schedule(() -> {
            System.out.println("60 seconds later");
        }, 60, TimeUnit.SECONDS);

        ch.eventLoop().scheduleAtFixedRate(() -> {
            System.out.println("60 seconds later");
        }, 60, 60, TimeUnit.SECONDS);
    }

}
