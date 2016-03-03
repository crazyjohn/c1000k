package com.crazyjohn.playboy;

import com.crazyjohn.playboy.logic.self.SelfController;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.Router;

/**
 * The playboy node;
 * <p>
 * easy scale out
 * 
 * @author crazyjohn
 *
 */
public class PlayBoyNode {
	static Logger logger = LoggerFactory.getLogger("PlayBoy");

	public static void main(String[] args) {
		Vertx vertx = Vertx.vertx();
		HttpServerOptions settings = new HttpServerOptions();
		HttpServer server = vertx.createHttpServer(settings);
		Router router = Router.router(vertx);
		router.route().handler(routingContext -> {
			routingContext.response().putHeader("content-type", "text/plain").end("Hello biatch, this is playboy!");
		});
		// upload
		router.route("/playboy/upload/head/").handler(SelfController::uploadHead);
		server.requestHandler(router::accept).listen(8080, result -> {
			if (result.succeeded()) {
				logger.info("The playboy ready!");
			}
		});
	}

}
