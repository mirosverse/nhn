package com.nhnacademy;

import java.net.Socket;

public class Exam03 {
    public static void main(String[] args) {
        String host = "localhost";
        int port = 1234;

        if (args.length >= 1) {
            host = args[0];
        }
        if (args.length >= 2) {
            port = Integer.parseInt(args[1]);
        }

        try (Socket socket = new Socket(host, port)) {
            System.out.println("서버에 연결되었습니다.");

            System.out.println("Local address : " + socket.getLocalAddress().getHostAddress());
            System.out.println("Local port : " + socket.getLocalPort());
            System.out.println("Remote address : " + socket.getInetAddress());
            System.out.println("Remote port : " + socket.getPort());

        } catch (Exception e) {
            System.out.println(host + ":" + port + "에 연결할 수 없습니다.");
        }
    }
}
