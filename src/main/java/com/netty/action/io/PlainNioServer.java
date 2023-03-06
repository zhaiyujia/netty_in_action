package com.netty.action.io;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @author zhai
 * @date 2023/3/1 10:19 AM
 * @illustration
 * @slogan: Treat others the way you want to be treated
 * @version:
 */
public class PlainNioServer {

    public void serve(int port) throws Exception {
        // 创建通道
        ServerSocketChannel serverChannel = ServerSocketChannel.open();
        serverChannel.configureBlocking(false);

        // 绑定地址
        ServerSocket socket = serverChannel.socket();
        InetSocketAddress address = new InetSocketAddress(port);
        socket.bind(address);

        // 注册selector 接收连接
        Selector selector = Selector.open();
        serverChannel.register(selector, SelectionKey.OP_ACCEPT);

        final ByteBuffer msg = ByteBuffer.wrap("Hi!\r\n".getBytes());

        for (; ; ) {
            // 等待需要处理的事件
            selector.select();

            // 获取所有接收事件的key类型
            Set<SelectionKey> readyKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = readyKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                iterator.remove();
                // 检查事件是否是一个就绪的可以被接收的连接
                if (key.isAcceptable()) {
                    ServerSocketChannel server = (ServerSocketChannel) key.channel();
                    SocketChannel client = server.accept();
                    // 接收客户端注册选择器
                    client.configureBlocking(false);
                    client.register(selector, SelectionKey.OP_WRITE | SelectionKey.OP_READ, msg.duplicate());
                    System.out.println("Acc from " + client);
                }

                // 检查套接字是否已经准备好写数据
                if (key.isWritable()) {
                    SocketChannel client = (SocketChannel) key.channel();
                    ByteBuffer buffer = (ByteBuffer) key.attachment();
                    while (buffer.hasRemaining()) {
                        // 将数据写到已连接的客户端
                        if (client.write(buffer) == 0) {
                            break;
                        }
                    }
                    client.close();
                }
                key.cancel();
                key.channel().close();
            }

        }


    }
}
