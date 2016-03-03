package com.crazyjohn.playboy.bot;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpClient;

public class PlayBot {

	public static void main(String[] args) {
		Vertx vertx = Vertx.vertx();
		HttpClient client = vertx.createHttpClient();
		client.getNow("localhost:8080", response -> {
			com.crazyjohn.playboy.log.Logger.log("Received response with status code " + response.statusCode());

		});
	}

}
