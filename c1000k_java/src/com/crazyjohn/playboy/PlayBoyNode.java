package com.crazyjohn.playboy;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.ext.web.Router;

import com.crazyjohn.playboy.log.Logger;
import com.crazyjohn.playboy.logic.self.Self;
import com.crazyjohn.playboy.logic.test.Test;

/**
 * The playboy node;
 * <p>
 * easy scale out
 * 
 * @author crazyjohn
 *
 */
public class PlayBoyNode {

	protected static void singleMode(Vertx vertx) {
		scaleOutMode(vertx, 1);
	}

	protected static void scaleOutMode(Vertx vertx, int instanceCount) {
		for (int i = 0; i < instanceCount; i++) {
			HttpServerOptions options = new HttpServerOptions();
			HttpServer server = vertx.createHttpServer(options);
			// create router
			Router router = Router.router(vertx);
			// test case
			Test test = new Test();
			router.route("/hi/").handler(test::sayHi);
			// upload case
			Self selfController = new Self();
			router.route("/upload/head/").handler(selfController::uploadHead);
			// start server
			server.requestHandler(router::accept).listen(8080, result -> {
				if (result.succeeded()) {
					Logger.log("The playboy ready!");
				} else {
					Logger.log("Some fucking error???: " + result.cause());
				}
			});
		}
	}

	/**
	 * entrance
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// create vertx instance
		Vertx vertx = Vertx.vertx();
		// scale mode
		int processorCount = 5;
		scaleOutMode(vertx, processorCount);
	}

}
