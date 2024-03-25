package com.nhnacademy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server implements Runnable {
    Scanner scanner = new Scanner(System.in);
    private String host;
    private int port;
    private boolean flag = true;

    public Server(String host, int port) {
        this.host = host;
        this.port = port;
    }

    @Override
    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            Socket socket = serverSocket.accept();
            System.out.println("Connected : " + socket.getInetAddress().getHostAddress() + ", " + socket.getPort());
            Thread thread = new Thread(() -> {
                try {
                    String line;
                    while ((line = scanner.nextLine()) != null && flag) {
                        socket.getOutputStream().write(("server: " + line + "\n").getBytes());
                    }
                } catch (Exception e) {
                    flag = false;
                    System.err.println(e.getMessage());
                }
            });
            thread.start();

            while (!Thread.currentThread().isInterrupted() && flag) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String line = reader.readLine();
                System.out.println("client: " + line);
            }
        } catch (IOException e) {
            flag = false;
            System.err.println(e.getMessage());
        }
    }

}
