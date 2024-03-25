package com.nhnacademy;

import java.net.ConnectException;
import java.net.Socket;
import java.util.Scanner;

public class Exam04 {
    public static void main(String[] args) {
        String host = "localhost";
        int port = 12345;
        try (Socket socket = new Socket(host, port)) {
            System.out.println("서버에 연결되었습니다.");

            Scanner scanner = new Scanner(socket.getInputStream());
            String str = "";
            while (scanner.hasNextLine() && !(str = scanner.nextLine()).equals("exit")) {
                System.out.println(str);
            }
            scanner.close();
        } catch (ConnectException e) {
            System.err.println(host + ":" + port + "에 연결할 수 없습니다.");
        } catch (Exception e) {
            //
        }
    }
}
