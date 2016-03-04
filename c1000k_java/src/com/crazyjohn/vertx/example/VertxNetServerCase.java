package com.crazyjohn.vertx.example;

import com.crazyjohn.playboy.log.Logger;

import io.vertx.core.Vertx;
import io.vertx.core.net.NetServer;

public class VertxNetServerCase {

	public static void main(String[] args) {
		Vertx vertx = Vertx.vertx();
		// single server
		// startupSingleServer(vertx);
		// multi servers
		startMultiServers(vertx, 10);
	}

	protected static void startMultiServers(Vertx vertx, int count) {
		for (int i = 0; i < count; i++) {
			NetServer server = vertx.createNetServer();
			// connect handler
			server.connectHandler(socket -> {
				socket.handler(buffer -> {
					// handle buffer
				});
			});
			// listen
			server.listen(8081, result -> {
				if (result.succeeded()) {
					Logger.log("NetServer start.");
				} else {
					Logger.log("NetServer fail.");
				}
			});
		}
	}

	protected static NetServer startupSingleServer(Vertx vertx) {
		NetServer server = vertx.createNetServer();
		// connect handler
		server.connectHandler(socket -> {
			socket.handler(buffer -> {
				// handle buffer
			});
		});
		// listen
		server.listen(8081, result -> {
			if (result.succeeded()) {
				Logger.log("NetServer start.");
			} else {
				Logger.log("NetServer fail.");
			}
		});
		return server;
	}
}
