package com.crazyjohn.playboy.scale;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;

import com.crazyjohn.playboy.controller.self.Self;
import com.crazyjohn.playboy.controller.test.Test;
import com.crazyjohn.playboy.log.Logger;

public class ScaleMode {

	public static void singleMode(Vertx vertx, int port) {
		scaleOutMode(vertx, 1, port);
	}

	public static void scaleOutMode(Vertx vertx, int instanceCount, int port) {
		for (int i = 0; i < instanceCount; i++) {
			HttpServerOptions options = new HttpServerOptions();
			HttpServer server = vertx.createHttpServer(options);
			// create router
			Router router = Router.router(vertx);
			// test case
			Test testController = new Test();
			router.route("/hi/").handler(testController::sayHi);
			// upload case
			Self selfController = new Self();
			// Enable multipart form data parsing
			Logger.log(String.format("Upload dir: %s", "/upload"));
			router.route().handler(BodyHandler.create().setUploadsDirectory("/upload"));
			// form
			router.route("/uploadForm/").handler(selfController::uploadForm);
			router.route("/form/").handler(selfController::form);
			// start server
			server.requestHandler(router::accept).listen(port, result -> {
				if (result.succeeded()) {
					Logger.log("The playboy ready!");
				} else {
					Logger.log("Some fucking error???: " + result.cause());
				}
			});
		}
	}

}
