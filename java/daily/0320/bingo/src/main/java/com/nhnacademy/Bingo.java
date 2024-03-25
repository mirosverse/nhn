package com.nhnacademy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Bingo {
    private int n;
    private int[][] board;
    private Color[][] coloredBoard;
    private int port = 12345;

    public Bingo(int n, int port) {
        this.n = n;
        this.board = new int[n][n];
        this.coloredBoard = new Color[n][n];
        this.port = port;
    }

    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            Socket socket1 = serverSocket.accept();
            Socket socket2 = serverSocket.accept();

            BufferedReader reader1 = new BufferedReader(new InputStreamReader(socket1.getInputStream()));
            BufferedReader reader2 = new BufferedReader(new InputStreamReader(socket2.getInputStream()));

            print(socket1, "==== Game Ready ====\n");
            print(socket2, "==== Game Ready ===\n");

            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board.length; j++) {
                    if (j % 2 == 0) {
                        print(socket1, (i + "줄 " + j + "열에 입력할 숫자는?\n"));
                        board[i][j] = Integer.parseInt(reader1.readLine());
                    } else {
                        print(socket2, (i + "줄 " + j + "열에 입력할 숫자?\n"));
                        board[i][j] = Integer.parseInt(reader2.readLine());
                    }
                    System.out.println(board[i][j]);
                }
            }

            print(socket1, "==== Game Start ===\n" + getBoard());
            print(socket2, "==== Game Start ====\n" + getBoard());

            int count = n * n;
            while (count-- > 0) {
                choice(socket1, reader1, Color.RED);
                print(socket1, getBoard());

                choice(socket2, reader2, Color.BLUE);
                print(socket2, getBoard());
                if (isWin(Color.BLUE) || isWin(Color.RED)) {
                    break;
                }
            }

            print(socket1, "게임이 끝났습니다.\n");
            print(socket2, "게임이 끝났습니다.\n");
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public void choice(Socket socket, BufferedReader reader, Color color) {
        try {
            int value;
            int[] index = new int[] { -1, -1 };
            while (index[0] == -1 || index[1] == -1) {
                print(socket, "번호를 입력하십시오 : ");
                value = Integer.parseInt(reader.readLine());
                index = findNumber(value);
                if (index[0] != -1 && coloredBoard[index[0]][index[1]] == null) {
                    coloredBoard[index[0]][index[1]] = color;
                    board[index[0]][index[1]] = 0;
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isWin(Color color) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (coloredBoard[i][j] == null)
                    break;
                if (!color.equals(coloredBoard[i][j]))
                    break;
                if (j == board.length - 1) {
                    System.out.println(coloredBoard[i][j] + ". " + i + ", " + j);
                    return true;
                }
            }
        }

        for (int j = 0; j < board.length; j++) {
            for (int i = 0; i < board.length; i++) {
                if (coloredBoard[i][j] == null)
                    break;
                if (!color.equals(coloredBoard[i][j]))
                    break;
                if (i == board.length - 1) {
                    System.out.println(coloredBoard[i][j] + ".. " + i + ", " + j);
                    return true;
                }
            }
        }

        for (int i = 0; i < board.length; i++) {
            if (!color.equals(coloredBoard[i][i]))
                break;
            if (i == board.length - 1) {
                System.out.println(coloredBoard[i][i] + ".. " + i);
                return true;
            }
        }

        int j = 0;
        for (int i = board.length - 1; i >= 0; i--) {
            if (!color.equals(coloredBoard[j++][i])) {
                break;
            }
            if (i == 0) {
                return true;
            }
        }

        return false;
    }

    public void print(Socket socket, String line) {
        try {
            socket.getOutputStream().write((line).getBytes());
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public int[] findNumber(int value) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (board[i][j] == value) {
                    return new int[] { i, j };
                }
            }
        }
        return new int[] { -1, -1 };
    }

    public String getBoard() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (coloredBoard[i][j] == null) {
                    sb.append(" " + board[i][j] + " ");
                } else if (coloredBoard[i][j].equals(Color.RED)) {
                    sb.append("[" + board[i][j] + "]");
                } else if (coloredBoard[i][j].equals(Color.BLUE)) {
                    sb.append(" XX ");
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
