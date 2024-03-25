package com.nhnacademy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;

public class SimpleNC {
    public static void main(String[] args) {
        Options options = new Options();

        options.addOption("p", "port", true, "Service Port");
        options.addOption("cl", "client-list", false, "Print client list");
        CommandLineParser parser = new DefaultParser();

        try {
            CommandLine commandLine = parser.parse(options, args);

            // systme에 들어오는 입력들을 netCat리스트에 싹 돌리기 (-> netCatList에서 정확한 포트번호 뽑아서 그놈한테만 돌리는 걸로
            // 변경)
            if (commandLine.hasOption("l")) {
                List<Thread> clientHandlerList = new LinkedList<>();
                List<NetCat> netcatList = new LinkedList<>();

                Thread inputAgent = new Thread(() -> {
                    try {
                        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                        String line;
                        while ((line = reader.readLine()) != null) {
                            synchronized (netcatList) {
                                for (NetCat netCat : netcatList) {
                                    netCat.send(line);
                                }
                            }
                        }
                    } catch (Exception e) {
                        //
                    }
                });

                // Client : queue에 있는 입력들을 싹 받기 (이건 그대로)
                Thread outputAgent = new Thread(() -> {
                    while (!Thread.currentThread().interrupted()) {
                        synchronized (netcatList) {
                            for (NetCat netCat : netcatList) {
                                if (!netCat.isEmptyReceiveQueue()) {
                                    String line = netCat.receive();
                                    System.out.println(line);
                                }
                            }
                        }
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (Exception e) {
                        //
                    }
                });

                inputAgent.start();
                outputAgent.start();

                // Server
                ServerSocket serverSocket = new ServerSocket(1234);
                while (!Thread.currentThread().isInterrupted()) {
                    try {
                        NetCat netCat = new NetCat(serverSocket.accept());
                        Thread thread = new Thread(netCat);
                        thread.start();
                        synchronized (netcatList) {
                            netcatList.add(netCat); // 주고받는걸 구현해줘야함. 별도로 스레드만들어서 주고받는걸
                        }
                    } catch (Exception e) {
                        System.err.println(e.getMessage());
                    }
                }
                serverSocket.close();
            } else {
                try (Socket socket = new Socket("localhost", 1234)) {
                    NetCat netCat = new NetCat(socket);
                    Thread thread = new Thread(netCat);
                    thread.start();
                    thread.join();
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
            }
        } catch (org.apache.commons.cli.ParseException |

                IOException e) {
            //
        }
    }
}