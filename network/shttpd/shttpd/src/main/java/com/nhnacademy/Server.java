package com.nhnacademy;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.LinkedList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Server implements Runnable {
	List<ServiceHandler> serviceHandlers = new LinkedList<>();
	Logger log;
	int port = 80;

	public Server(int port) {
		log = LogManager.getLogger(this.getClass().getSimpleName());
		this.port = port;
	}

	@Override
	public void run() {
		log.trace("Start server : {}", port);
		while (!Thread.currentThread().isInterrupted()) {
			try (ServerSocket socket = new ServerSocket(port)) {
				ServiceHandler handler = new ServiceHandler(socket.accept());
				handler.start();
				serviceHandlers.add(handler);
			} catch (IOException ignore) {
				//
			}
		}

		log.trace("Stopped server : {}", port);
		for (ServiceHandler serviceHandler : serviceHandlers) {
			serviceHandler.stop();
		}
	}
}
