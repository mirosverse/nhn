package com.nhnacademy;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ServiceHandler implements Runnable {
    static final String CRLF = "\r\n";
    Socket socket;
    Thread thread;
    Logger log;

    public ServiceHandler(Socket socket) {
        log = LogManager.getLogger(this.getClass().getSimpleName());
        this.socket = socket;
        thread = new Thread(this);
    }

    public void start() {
        thread.start();
    }

    public void stop() {
        try {
            thread.interrupt();
            thread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

    }

    String getFileList(Path path) {
        StringBuilder builder = new StringBuilder();

        try (Stream<Path> stream = Files.list(path)) {
            stream.filter(file -> !Files.isDirectory(file))
                    .map(Path::getFileName)
                    .forEach(p -> builder.append(p.toString()).append(CRLF));
        } catch (IOException ignore) {
            throw new InvalidStatusException(403);
        }

        return builder.toString();
    }

    String getFile(Path path) {
        StringBuilder builder = new StringBuilder();

        try (Stream<String> lines = Files.lines(path)) {
            lines.forEach(x -> builder.append(x).append(CRLF));
        } catch (IOException e) {
            throw new InvalidStatusException(403);
        }

        return builder.toString();
    }

    // response 처리
    public Response process(Request request) {
        try {
            if (request.getMethod().equals("GET")) {
                Path relativePath = Paths.get("." + request.getPath());

                Response response = new Response(request.getVersion(), 200, "OK");
                StringBuilder contentType = new StringBuilder();
                contentType.append("text");

                // 프로그램이 실행된 디렉토리를 document-root로 설정
                if (Files.isDirectory(relativePath)) {
                    contentType.append("; charset=utf-8");
                    response.setBody(getFileList(relativePath).getBytes(StandardCharsets.UTF_8));
                } else if (Files.isRegularFile(relativePath)) {
                    String filename = relativePath.getFileName().toString();

                    // Content-type: text/{이 부분} 설정
                    contentType.append("/")
                            .append(filename.substring(filename.lastIndexOf(".") + 1))
                            .append("; charset=utf-8");

                    response.setBody(getFile(relativePath).getBytes(StandardCharsets.UTF_8));
                } else {
                    throw new InvalidStatusException(404, "Not Found");
                }

                // body { byte[] } 설정
                response.addField("content-type", contentType.toString());
                log.trace("response: {}", response);
                return response;
            } else if (request.getMethod().equals("POST")) {

                // 파일 읽어오기 
                if (request.getField("content-type").equals("multipart/form-data")) {

                }

            }

            throw new InvalidStatusException(400);
        } catch (InvalidStatusException e) {
            Response response = new Response(request.getVersion(), e.getCode(), e.getMessage());
            response.setBody((e.toString() + CRLF).getBytes(StandardCharsets.UTF_8));

            String filename = Paths.get("." + request.getPath()).getFileName().toString();
            StringBuilder contentType = new StringBuilder();
            contentType.append("text");
            contentType.append("/")
                    .append(filename.substring(filename.lastIndexOf(".") + 1))
                    .append("; charset=utf-8");

            response.addField("content-type", contentType.toString());
            return response;
        }
    }

    // requset 처리 중점
    @Override
    public void run() {
        log.trace("Start thread : {}", thread.getId());

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                BufferedOutputStream writer = new BufferedOutputStream(socket.getOutputStream())) {
            while (!Thread.currentThread().isInterrupted()) {
                String requestLine = reader.readLine();
                if (requestLine == null) {
                    break;
                }

                String[] fields = requestLine.split("\\s", 3);
                if (fields.length != 3) {
                    throw new IllegalArgumentException();
                }

                Request request = new Request(fields[0], fields[1], fields[2]);

                String fieldLine;
                while ((fieldLine = reader.readLine()) != null) {
                    if (fieldLine.length() == 0) {
                        break;
                    }
                    request.addField(fieldLine);
                }

                if (request.hasField(Request.FIELD_CONTENT_LENGTH)) {
                    char[] buffer = new char[request.getContentLength()];

                    int bodyLength = reader.read(buffer, 0, request.getContentLength());
                    if (bodyLength == request.getContentLength()) {
                        request.setBody(buffer);
                    }
                }

                Response response = process(request);
                log.trace(response);

                writer.write(response.getBytes());
                writer.flush();

            }

        } catch (Exception ignore) {
            //
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                //
            }
        }
    }

}
