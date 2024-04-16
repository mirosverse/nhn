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

public class Client implements Runnable {
    private String id;

    Socket socket;
    Queue<String> receiverQueue = new LinkedList<>();
    Queue<String[]> senderQueue = new LinkedList<>();

    InputStream inputRemoteStream;
    OutputStream outputRemoteStream;
    InputStream inputLocalStream;
    OutputStream outputLocalStream;

    public Client(Socket socket) {
        this.socket = socket;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String[] send() {
        synchronized (senderQueue) {
            return senderQueue.poll();
        }
    }

    public void receive(String message) {
        synchronized (receiverQueue) {
            receiverQueue.add(message);
        }
    }

    public boolean isEmptySenderQueue() {
        synchronized (senderQueue) {
            return senderQueue.isEmpty();
        }
    }

    @Override
    public void run() {
        try {
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter output = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            output.write("Enter your ID: ");
            output.flush();
            setId(input.readLine());

            Thread sender = new Thread(() -> {
                try {

                    String line, target;
                    output.write("Target ID :");
                    output.flush();
                    while ((target = input.readLine()) != null) {
                        output.write("message : ");
                        output.flush();
                        line = input.readLine();
                        synchronized (senderQueue) {
                            senderQueue.add(new String[] { target, line });
                        }
                        output.write("Target ID :");
                        output.flush();
                    }
                    
                } catch (IOException e) {
                    System.err.println(e.getMessage());
                }
            });

            Thread receiver = new Thread(() -> {
                try {
                    while (!Thread.currentThread().isInterrupted()) {
                        synchronized (receiverQueue) {
                            if (!receiverQueue.isEmpty()) {
                                output.write("\n" + receiverQueue.poll() + "\n");
                                output.flush();
                            }
                        }
                    }
                } catch (Exception e) {
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
