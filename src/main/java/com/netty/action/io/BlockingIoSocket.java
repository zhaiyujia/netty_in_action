package com.netty.action.io;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author zhai
 * @date 2023/2/28 3:00 PM
 * @illustration
 * @slogan: Treat others the way you want to be treated
 * @version:
 */
public class BlockingIoSocket {

    /**
     * socket      socket
     *   |           |
     *  r/w         r/w
     *   |           |
     *  thread     thread
     * 阻塞I/O处理多个连接的缺点
     *  1、等待数据连接，线程搁置，浪费资源
     *  2、浪费内存，每个线程需要分配64kb到1M的存储，依赖于OS分配
     *  3、JVM不支持大量的线程连接，导致连接数超过限制
     * @param args
     */
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(8080)) {
            Socket clientSocket = serverSocket.accept();
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            String request, response;
            while ((request = in.readLine()) != null) {
                if ("Done".equals(request)) {
                    break;
                }
                response = processRequest(request);
                out.println(response);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private static String processRequest(String request) {
        return null;
    }

}
