package com.crazyjohn.vertx.example;

import com.crazyjohn.playboy.log.Logger;

import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.MessageConsumer;

public class VertxClusterCase {

	public static void main(String[] args) throws Exception {
		VertxOptions option = new VertxOptions();
		option.setClustered(true);
		// cluster vertx
		Vertx.clusteredVertx(option, vertx -> {
			if (vertx.succeeded()) {
				Vertx shard = vertx.result();
				EventBus bus = shard.eventBus();
				Logger.log(String.format("Now we have a cluster eventbus: %s", bus));
				// consumer
				MessageConsumer<String> consumer = bus.consumer("playboy.cluster.event.shudown");
				consumer.handler(msg -> {
					Logger.log("ClusterEvent: " + msg.body());
					System.exit(0);
				});
				consumer.completionHandler(result -> {
					if (result.succeeded()) {
						Logger.log("Register handler to all nodes.");
						try {
							// Thread.sleep(10 * 1000);
							bus.publish("playboy.cluster.event.shudown", "Cluster Shutdown");
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				});
			} else {
				Logger.log(String.format("Faied: %s", vertx.cause()));
			}
		});
	}

}
