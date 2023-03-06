package com.netty.action.io;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;

/**
 * @author zhai
 * @date 2023/3/1 10:12 AM
 * @illustration
 * @slogan: Treat others the way you want to be treated
 * @version:
 */
public class PlainOioServer {

    public void serve(int port) throws Exception {
        final ServerSocket socket = new ServerSocket(port);
        try {
            for (;;){
                Socket clientSocket =  socket.accept();
                System.out.println("Accepted");

                new Thread(()->{
                    OutputStream out;
                    try {
                        out = clientSocket.getOutputStream();
                        out.write("Hi!\r\n".getBytes(Charset.forName("UTF-8")));
                        out.close();
                        clientSocket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }).start();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
