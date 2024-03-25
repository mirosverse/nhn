package com.nhnacademy;

import java.net.ServerSocket;
import java.net.Socket;

public class Exam_04 {
    public static void main(String[] args) {
        String host = "localhost";
        int port = 12345;

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            Socket socket = serverSocket.accept();
            socket.getOutputStream().write("hello\n".getBytes());
            socket.close();
        } catch (Exception e) {
            // 
        }
    }
}
