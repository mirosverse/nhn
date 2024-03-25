package com.nhnacademy;

import java.net.Socket;

public class Exam01 {
    public static void main(String[] args) {
        int startPort = Integer.parseInt(args[0]);
        int endPort = Integer.parseInt(args[1]);
        for (int port = startPort; port < endPort; port++) {
            try (Socket socket = new Socket("localhost", port)) {
                System.out.println(port + "가 열려 있습니다.");
            } catch (Exception e) {
                //
            }
        }
    }
}
