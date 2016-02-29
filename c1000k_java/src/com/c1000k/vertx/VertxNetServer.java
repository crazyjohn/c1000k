package com.c1000k.vertx;

import io.vertx.core.Vertx;
import io.vertx.core.net.NetServer;

public class VertxNetServer {

	public static void main(String[] args) {
		Vertx vert = Vertx.vertx();
		NetServer server = vert.createNetServer();
		// connection handler
		server.connectHandler(socket -> {
			System.out.println("Connection handler: %s" + socket.toString());
		});
		server.listen(8080, "localhost", result -> {
			if (result.succeeded()) {
				System.out.println("Server is now listening");
			} else {
				System.out.println("Faild to bind");
			}
		});
	}
}
