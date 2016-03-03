package com.crazyjohn.playboy;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.ext.web.Router;

import com.crazyjohn.playboy.log.Logger;
import com.crazyjohn.playboy.logic.self.Self;

/**
 * The playboy node;
 * <p>
 * easy scale out
 * 
 * @author crazyjohn
 *
 */
public class PlayBoyNode {

	public static void main(String[] args) {
		Vertx vertx = Vertx.vertx();
		HttpServerOptions settings = new HttpServerOptions();
		HttpServer server = vertx.createHttpServer(settings);
		Router router = Router.router(vertx);
		router.route("/hi/").handler(routingContext -> {
			routingContext.response().putHeader("content-type", "text/plain").end("Hello biatch, this is playboy!");
		});
		// upload
		Self selfController = new Self();
		router.route("/upload/head/").handler(selfController::uploadHead);
		// start server
		server.requestHandler(router::accept).listen(8080, result -> {
			if (result.succeeded()) {
				Logger.log("The playboy ready!");
			}
		});
	}

}
