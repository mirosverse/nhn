package com.nhnacademy;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Queue;

public class NetCat implements Runnable {
    Socket socket;
    Queue<String> receiverQueue = new LinkedList<>();
    Queue<String> senderQueue = new LinkedList<>();

    InputStream inputRemoteStream;
    OutputStream outputRemoteStream;
    InputStream inputLocalStream;
    OutputStream outputLocalStream;

    public NetCat(Socket socket) {
        this.socket = socket;
    }

    public void send(String message) {
        synchronized (senderQueue) {
            senderQueue.add(message);
        }
    }

    public String receive() {
        synchronized (receiverQueue) {
            return receiverQueue.poll();
        }
    }

    public boolean isEmptyReceiveQueue() {
        synchronized (receiverQueue) {
            return receiverQueue.isEmpty();
        }
    }

    public void run() {
        try (BufferedReader inputRemote = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                BufferedWriter outputRemote = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))) {

            Thread receiver = new Thread(() -> {
                try {
                    String line;
                    while ((line = inputRemote.readLine()) != null) {
                        synchronized (receiverQueue) {
                            receiverQueue.add(line);
                        }
                    }
                } catch (IOException e) {
                    System.err.println(e.getMessage());
                }
            });

            Thread sender = new Thread(() -> {
                try {
                    while (!Thread.currentThread().isInterrupted()) {
                        synchronized (senderQueue) {
                            if (!senderQueue.isEmpty()) {
                                outputRemote.write(senderQueue.poll());
                                outputRemote.flush();
                            }
                        }
                    }
                } catch (IOException e) {
                    System.err.println(e.getMessage());
                }
            });

            receiver.start();
            sender.start();

            receiver.join();
            sender.join();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
        }
    }
}
