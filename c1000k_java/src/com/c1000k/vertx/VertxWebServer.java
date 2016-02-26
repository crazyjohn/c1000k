package com.c1000k.vertx;

import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;

public class VertxWebServer {

	public static void main(String[] args) {
		Vertx vertx = Vertx.vertx(new VertxOptions().setWorkerPoolSize(4));
		HttpServer server = vertx.createHttpServer();
		Router router = Router.router(vertx);
		router.route().handler(
				routingContext -> {
					routingContext.response()
							.putHeader("content-type", "text/plain")
							.end("Hello World from vertx-web.");
				});
		server.requestHandler(router::accept).listen(8080);
	}

}
