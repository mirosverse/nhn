package com.nhnacademy;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Server implements Runnable {
    private DB db = new DB();
    private static int mesaageId = 0;
    List<Client> clients = new LinkedList<>();
    List<String> denyClients = new LinkedList<>();
    Queue<Request> requestQueue = new LinkedList<>();
    Queue<Response> responseQueue = new LinkedList<>();
    HashMap<Integer, String> messages = new HashMap();

    private int port;

    public Server(int port) {
        this.port = port;
    }

    public void addDenyClient(String clientId) {
        denyClients.add(clientId);
    }

    public void delDenyClient(String clientId) {
        denyClients.remove(clientId);
    }

    public void sendOff(String clientId) {
        for (Client client : clients) {
            if (client.getId().equals(clientId)) {
                clients.remove(client); // ****** client의 run 스레드가 동작중지되게 만들어야함
            }
        }

    }

    public synchronized boolean containsID(String id) {
        for (Client client : clients) {
            if (client.getId().equals(id))
                return true;
        }
        return false;
    }

    @Override
    public void run() {
        // 접속 요청 (요청)
        // systme에 들어오는 입력들을 받아서 클라이언트의 목표에게 주기 (XXXXXXXXXXXX 이거 클라이언트쪽으로 옮겨서 클라이언트가 해당
        // 목표 호스트에게 보내면 해당 호스트의 리시브큐에 보내지는 걸로 해야함)
        Thread requestAgent = new Thread(() -> {
            try {
                while (!Thread.currentThread().interrupted()) {
                    synchronized (clients) {
                        for (Client client : clients) {
                            if (!client.isEmptySenderQueue()) {
                                String[] sendInfo = client.send();
                                Request request = new Request(++mesaageId, Type.CONNECT, sendInfo[0]);
                                requestQueue.add(request); // 0: target_ID,
                                messages.put(mesaageId, "ID" + client.getId() + " : " + sendInfo[1]);
                                db.appendReqDB(request);
                            }
                        }
                    }
                    try {
                        Thread.sleep(300);
                    } catch (Exception e) {
                        //
                    }
                }
            } catch (Exception e) {
                //
            }
        });

        // 접속 요청 (응답)
        // Client : recievequeue에 있는 입력들 처리 : Request 객체 생성
        Thread responseAgent = new Thread(() -> {
            while (!Thread.currentThread().interrupted()) {
                synchronized (requestQueue) {
                    Iterator<Request> it = requestQueue.iterator();
                    while (it.hasNext()) {
                        Request request = requestQueue.poll();
                        String str = "ok";
                        if (!containsID(request.getClient_id())) {
                            str = "deny";
                        }
                        Response response = new Response(request.getId(), request.getType(), str,
                                request.getClient_id());
                        responseQueue.add(response);
                        db.appendResDB(response);
                    }
                }
                try {
                    Thread.sleep(300);
                } catch (Exception e) {
                    //
                }
            }
        });

        // 메시지 전송
        Thread agent = new Thread(() -> {
            while (!Thread.currentThread().interrupted()) {
                synchronized (responseQueue) {
                    Iterator<Response> it = responseQueue.iterator();
                    while (it.hasNext()) {
                        Response response = responseQueue.poll();
                        if (response.getResponse().equals("ok")) {
                            for (Client client : clients) {
                                if (client.getId().equals(response.getClient_id())) {
                                    db.appendMesDB(new Message(response.getId(), Type.MESSAGE,
                                            response.getClient_id(), messages.get(response.getId())));
                                    client.receive(messages.get(response.getId()));
                                }
                            }
                        }
                        messages.remove(response.getId());
                    }
                }
                try {
                    Thread.sleep(300);
                } catch (Exception e) {
                    //
                }
            }
        });

        requestAgent.start();
        responseAgent.start();
        agent.start();

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    Client client = new Client(serverSocket.accept());
                    Thread thread = new Thread(client);
                    thread.start();
                    clients.add(client); // 주고받는걸 구현해줘야함. 별도로 스레드만들어서 주고받는걸

                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
