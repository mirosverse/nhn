package com.nhnacademy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Quiz09 {
    public static void main(String[] args) {
        // String host = "localhost";
        int port = 12345;

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (!Thread.currentThread().isInterrupted()) {
                Socket socket = serverSocket.accept();
                System.out.println("Connected : " + socket.getInetAddress().getHostAddress());

                try {
                    String line;
                    BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    while (!(line = reader.readLine()).equals("exist")) {
                        System.out.println(line);
                        socket.getOutputStream().write((line + "\n").getBytes());
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
                socket.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
