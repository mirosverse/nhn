package com.nhnacademy;

import java.net.ConnectException;
import java.net.Socket;

public class Exam02 {
    public static void main(String[] args) {
        String host = "localhost";
        int port = 12345;
        try (Socket socket = new Socket(host, port)) {
            System.out.println("서버에 연결되었습니다.");

            socket.getOutputStream().write("Hello!".getBytes());
        } catch (ConnectException e) {
            System.err.println(host + ":" + port + "에 연결할 수 없습니다.");
        } catch (Exception e) {
            // 
        }

    }
}
