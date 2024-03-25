package com.nhnacademy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Scanner;

public class Client implements Runnable {
    Scanner scanner = new Scanner(System.in);
    private String host;
    private int port;
    private boolean flag = true;

    public Client(String host, int port) {
        this.host = host;
        this.port = port;
    }

    @Override
    public void run() {
        try (Socket socket = new Socket(host, port)) {
            System.out.println("Connected : " + socket.getLocalAddress() + ", " + socket.getLocalPort());
            Thread severThread = new Thread(() -> {
                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    String line;
                    while ((line = reader.readLine()) != null && flag) {
                        System.out.println("server: " + line);
                    }
                } catch (Exception e) {
                    flag = false;
                    System.err.println(e.getMessage());
                }
            });
            severThread.start();

            while (!Thread.currentThread().isInterrupted() && flag) {                
                String line = scanner.nextLine();
                socket.getOutputStream().write(("client: " + line + "\n").getBytes());
            }

        } catch (IOException e) {
            flag = false;
            System.err.println(e.getMessage());
        }
    }

}
