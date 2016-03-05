package com.crazyjohn.playboy.logic.test;

import java.util.concurrent.atomic.AtomicInteger;

import io.vertx.ext.web.RoutingContext;

import com.crazyjohn.playboy.log.Logger;

public class Test {
	static AtomicInteger count = new AtomicInteger(0);

	public void sayHi(RoutingContext context) {
		context.response().putHeader("content-type", "text/plain").end("Hello biatch, this is playboy!");
		Logger.log("Say hi: " + count.incrementAndGet());
	}
}
