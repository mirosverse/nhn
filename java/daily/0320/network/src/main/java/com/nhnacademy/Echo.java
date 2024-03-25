package com.nhnacademy;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.Socket;

public class Echo {

    public static void main(String[] args) {
        String host = "localhost";
        int port = 12345;
        try (Socket socket = new Socket(host, port)) {
            System.out.println("서버에 연결되었습니다.");

            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String line = "";
            while (true) {
                line = input.readLine();
                if (line.equals("exit")) {
                    break;
                }
                socket.getOutputStream().write((line + "\n").getBytes());
            }
        } catch (ConnectException e) {
            System.err.println(host + ":" + port + "에 연결할 수 없습니다.");
        } catch (Exception e) {
            //
        }

    }
}